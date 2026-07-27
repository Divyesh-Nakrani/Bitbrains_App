# Bit Brains — Mobile App Integration Guide

Everything the mobile app (Android first, iOS notes included) needs to talk to this backend:
what to build, which API to call at which moment, the exact request/response shapes, and the
push payload contract.

Backend = FastAPI service that watches the user's Microsoft Teams chats. If a message sits
**unanswered for 30 seconds** (no reply, no emoji reaction), the backend escalates it and sends
a **data-only FCM push** to the user's phone. The app's job: raise a full-screen alarm, and tell
the backend what the user did (`READ` / `IGNORED`).

---

## 1. The whole flow in one picture

```
┌── ONE TIME, per install ────────────────────────────────────────────────┐
│ 1. App launch      → Firebase gives an FCM token                        │
│ 2. Login           → Chrome Custom Tab → GET /login → Microsoft consent │
│                    → deep link back with ?login=success&token=<JWT>     │
│ 3. Save token      → POST /api/devices/register  (Bearer + employeeId)  │
│                      response { linked: true }  ← MUST be true          │
└─────────────────────────────────────────────────────────────────────────┘

┌── EVERY DAY, in the background ─────────────────────────────────────────┐
│  Someone messages the user in Teams                                     │
│        ↓ (backend: webhook → store → arm 30s cooldown)                  │
│  30 seconds pass, user did not reply / react                            │
│        ↓                                                                │
│  FCM data-only push  →  FirebaseMessagingService.onMessageReceived()    │
│        ↓                                                                │
│  App shows FULL-SCREEN ALERT (sound + vibration, over lockscreen)       │
│        ↓                                                                │
│  User taps "Read"                → POST /api/messages/{id}/action READ  │
│  User taps "Ignore" / dismisses  → POST /api/messages/{id}/action IGNORED│
└─────────────────────────────────────────────────────────────────────────┘

┌── ON LOGOUT / TOKEN ROTATION ───────────────────────────────────────────┐
│  onNewToken(newToken)  → POST /api/devices/register  (again, idempotent) │
│  Logout                → POST /api/devices/unregister  then  POST /logout│
└─────────────────────────────────────────────────────────────────────────┘
```

---

## 2. Base URL & environments

| Environment | Base URL |
|-------------|----------|
| Local dev (emulator) | `http://10.0.2.2:8000` (`localhost` of the host machine) |
| Local dev (real device) | `http://<your-pc-lan-ip>:8000` — same Wi-Fi |
| Tunnel / staging | `https://<ngrok-id>.ngrok-free.app` |
| Production | TBD |

Put it in `BuildConfig.API_BASE_URL` (one per build flavour). Never hardcode in call sites.
Cleartext HTTP only for debug builds — add a `network_security_config.xml` that allows it for
the dev host, and keep release builds HTTPS-only.

---

## 3. Firebase setup (must match exactly)

| Item | Value |
|------|-------|
| Firebase project | `bir-brains` (project number `55325740421`) |
| Android package name | `com.bitcoding.bitbrains` — **must be exactly this** |
| Client config file | `google-services.json` → put at `app/google-services.json` |
| App id | `1:55325740421:android:5eb603f65610da0d1fc8ec` |

`google-services.json` is already in this repo root — copy it into the Android project.
Do **not** ask for `service-account.json`; that is the server's private key and never ships
inside the app.

If the package name differs, FCM answers `SenderIdMismatchError`, the backend marks that device
inactive, and pushes silently stop. This is the single most common integration failure.

---

## 4. Android requirements

### 4.1 Gradle

```kotlin
// app/build.gradle.kts
plugins {
    id("com.google.gms.google-services")
}
dependencies {
    implementation(platform("com.google.firebase:firebase-bom:33.7.0"))
    implementation("com.google.firebase:firebase-messaging")
    implementation("com.squareup.retrofit2:retrofit:2.11.0")
    implementation("com.squareup.retrofit2:converter-gson:2.11.0")
    implementation("com.squareup.okhttp3:logging-interceptor:4.12.0")
    implementation("androidx.browser:browser:1.8.0")          // Chrome Custom Tabs
    implementation("androidx.security:security-crypto:1.1.0-alpha06") // EncryptedSharedPreferences
    implementation("androidx.work:work-runtime-ktx:2.9.1")    // retry queue for actions
}
```

`minSdk 24`, `targetSdk 35`.

### 4.2 Manifest permissions

```xml
<uses-permission android:name="android.permission.INTERNET"/>
<uses-permission android:name="android.permission.POST_NOTIFICATIONS"/>      <!-- Android 13+ runtime -->
<uses-permission android:name="android.permission.USE_FULL_SCREEN_INTENT"/>  <!-- Android 14+ -->
<uses-permission android:name="android.permission.VIBRATE"/>
<uses-permission android:name="android.permission.WAKE_LOCK"/>
<uses-permission android:name="android.permission.RECEIVE_BOOT_COMPLETED"/>
```

Runtime work the app must do on first launch:

1. Request `POST_NOTIFICATIONS` (Android 13+) — without it no alert is ever shown.
2. On Android 14+, `USE_FULL_SCREEN_INTENT` is granted only to calling/alarm-style apps;
   otherwise send the user to `Settings.ACTION_MANAGE_APP_USE_FULL_SCREEN_INTENT` once.
3. Ask to disable battery optimisation (`ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS`) —
   otherwise Doze delays the alert on OEM ROMs (Xiaomi / Oppo / Vivo / Samsung are the worst).
4. On Chinese OEMs, also point the user at "Autostart" / "Allow background activity".

### 4.3 Notification channel (create once, at app start)

```kotlin
NotificationChannel("critical_alerts", "Unanswered Teams messages", IMPORTANCE_HIGH).apply {
    enableVibration(true)
    setBypassDnd(true)
    lockscreenVisibility = Notification.VISIBILITY_PUBLIC
    setSound(alarmSoundUri, AudioAttributes.Builder()
        .setUsage(AudioAttributes.USAGE_ALARM).build())
}
```

Channel importance can never be raised after creation — get it right the first time or bump the
channel id (`critical_alerts_v2`).

---

## 5. Identity model — read this before writing any code

The backend knows the user by `users.id` (UUID). The app identifies itself by **`employeeId`**,
a free-form string that the backend resolves in this order:

1. Authenticated session (`Authorization: Bearer <token>`) — most reliable
2. `employeeId` parsed as a `users.id` UUID
3. `employeeId` matched against the Microsoft Graph user id
4. `employeeId` matched against the email

**Recommendation: always send the user's Microsoft email as `employeeId`, and send the Bearer
token whenever you have one.** Then the device row is linked immediately and pushes can reach it.

A device registered with an `employeeId` that resolves to nobody is stored with
`linked: false` — it exists but **can never be pushed to** until the user completes the Microsoft
login, at which point `/auth/callback` claims it automatically.

### Auth token

- Type: signed, stateless bearer token issued by the backend (not a Microsoft token).
- Lifetime: **7 days**. There is no refresh endpoint — after expiry, log in again.
- Storage: `EncryptedSharedPreferences`. Never plain `SharedPreferences`, never logs.
- Send as `Authorization: Bearer <token>` on every authenticated call.
- Microsoft access/refresh tokens never leave the server. The app never sees them.

---

## 6. API reference (only what the app touches)

All bodies are JSON. Mobile endpoints under `/api` use **camelCase**; the older `/messages`,
`/profile` endpoints use **snake_case** — do not mix them up when writing models.

### 6.1 `GET /health` — no auth

Call on app start to show a "backend reachable" state and to verify push is configured.

```json
{ "status": "ok", "ack_cooldown_seconds": 30, "ack_monitor_enabled": true,
  "webhook_ready": true, "notification_channel": "webhook",
  "push_ready": true, "push_error": null }
```

`push_ready: false` → the server has no Firebase key; alerts will never arrive. Surface this in
a debug screen, it saves hours.

### 6.2 `GET /auth/status` — bearer optional

Call on every app launch to decide between the login screen and the dashboard.

```json
{ "authenticated": true, "display_name": "Jaydip", "email": "jaydip@contoso.com" }
```

or `{ "authenticated": false }` — token missing, invalid, or expired → show login.

### 6.3 `GET /login` — browser navigation only

**Never call this with Retrofit/OkHttp.** It is a 302 to Microsoft's consent page. Open it in a
Chrome Custom Tab:

```kotlin
CustomTabsIntent.Builder().build()
    .launchUrl(context, Uri.parse("$BASE_URL/login"))
```

After consent the backend redirects to whatever `FRONTEND_REDIRECT_URL` is set to, appending
`?login=success&token=<session-token>` (or `?login=error&message=...`).

For the app, ask the backend team to set:

```
FRONTEND_REDIRECT_URL=bitbrains://auth/callback
ISSUE_REDIRECT_TOKEN=true
```

and register the deep link:

```xml
<activity android:name=".auth.AuthCallbackActivity" android:exported="true">
  <intent-filter android:autoVerify="false">
    <action android:name="android.intent.action.VIEW"/>
    <category android:name="android.intent.category.DEFAULT"/>
    <category android:name="android.intent.category.BROWSABLE"/>
    <data android:scheme="bitbrains" android:host="auth"/>
  </intent-filter>
</activity>
```

`AuthCallbackActivity` reads `token` from the intent data, stores it encrypted, and continues to
step 6.4.

> ⚠️ `FRONTEND_REDIRECT_URL` is a **single global setting** on the backend. Pointing it at the
> app's deep link breaks the web/Streamlit login at the same time. If both must work, the backend
> needs a small change (e.g. `/login?client=mobile` choosing the redirect target). Flag it early.

### 6.4 `POST /api/devices/register` — auth optional (send it anyway)

Called: right after login, on every `onNewToken`, and once per app cold start (idempotent per
FCM token).

Request:

```json
{
  "employeeId": "jaydip@contoso.com",
  "fcmToken": "<FCM registration token>",
  "platform": "android",
  "deviceId": "a1b2c3d4e5f6",
  "deviceModel": "Pixel 8",
  "appVersion": "1.0.3"
}
```

| Field | Type | Required | Notes |
|-------|------|----------|-------|
| `employeeId` | string (1–256) | ✅ | User's Microsoft email. Must not be blank |
| `fcmToken` | string (1–1024) | ✅ | From `FirebaseMessaging.getInstance().token` |
| `platform` | string (≤32) | – | `"android"` (default) or `"ios"` |
| `deviceId` | string (≤128) | – | Any stable install id (support only) |
| `deviceModel` | string (≤128) | – | Support only |
| `appVersion` | string (≤64) | – | Support only |

Response `200`:

```json
{ "success": true, "deviceId": "3f1e…", "linked": true, "detail": null }
```

**Check `linked`.** If `false`, the response `detail` explains it and the app must show
"Complete Microsoft login to receive alerts" — that device gets no pushes until then.

`422` = validation error (blank field / too long).

### 6.5 `POST /api/devices/unregister` — no auth

Called on logout and before uninstall-ish flows. Deactivates the token so the backend stops
pushing.

```json
{ "fcmToken": "<token>" }
```
→ `{ "success": true, "removed": true }` (`removed: false` = token was unknown, harmless).

### 6.6 `GET /api/devices` — **auth required**

Debug/settings screen: which of this user's devices are registered.

```json
{ "count": 1, "devices": [{
  "id": "…", "employeeId": "jaydip@contoso.com", "userId": "…",
  "platform": "android", "deviceId": "a1b2…", "deviceModel": "Pixel 8",
  "appVersion": "1.0.3", "fcmTokenPreview": "dGhpcyBpcyB…x9Kp2Q",
  "isActive": true, "registeredAt": "…", "lastSeenAt": "…", "lastPushAt": "…",
  "failureCount": 0, "lastError": null
}]}
```

The full FCM token is never echoed back.

### 6.7 `POST /api/messages/{messageId}/action` — auth optional

**This is the most important call in the app.** Send it the moment the user reacts to an alert.

`{messageId}` = the `messageId` string from the push payload, sent back untouched.

```json
{
  "action": "READ",
  "employeeId": "jaydip@contoso.com",
  "actionTimestamp": 1769342431000,
  "fcmToken": "<token>"
}
```

| Field | Type | Required | Notes |
|-------|------|----------|-------|
| `action` | `"READ"` \| `"IGNORED"` | ✅ | Case-insensitive, normalised upper-case |
| `employeeId` | string | ⚠️ | Required **unless** a Bearer token or `fcmToken` is sent |
| `actionTimestamp` | int | – | Epoch **milliseconds**, device clock. Stored, never trusted |
| `fcmToken` | string | – | Attributes the action to a specific install |

Semantics — get this right:

- **`READ`** → acknowledges the message. Escalation stops, `ack_source = device`.
- **`IGNORED`** → recorded only. The message stays escalated. This is intentional: dismissing an
  alert without answering is exactly what the system exists to surface.

Response `200`:

```json
{ "success": true, "action_id": "…", "message_id": "…",
  "resolved": true, "acknowledged": true }
```

- `resolved: false` → the backend could not match that `messageId` (e.g. a `dev.test` push).
  It still returns `200` and records the action. **Do not treat this as an error, do not retry.**
- `400` → none of `employeeId` / `fcmToken` / Bearer was supplied. Fix the request; retrying the
  same body will fail again.

### 6.8 In-app message list (optional screen) — **auth required**

| Method | Path | Purpose |
|--------|------|---------|
| `GET` | `/messages?ack_status=escalated&limit=50&offset=0` | Paged list; `ack_status` ∈ `pending`/`acknowledged`/`escalated`/`skipped` |
| `GET` | `/messages/unacknowledged` | Shortcut for everything escalated |
| `GET` | `/messages/{message_id}` | One message + full acknowledgement state |
| `POST` | `/messages/{message_id}/acknowledge` | "Mark as seen" button (snake_case route, `ack_source = manual`) |
| `GET` | `/notifications?limit=50` | Delivery history — debug screen |
| `GET` | `/profile` | `graph_user_id`, `display_name`, `email`, `tenant_id` |
| `GET` | `/chats` | Stored chat list |
| `POST` | `/logout` | Clears the server-side session |

`GET /messages` response (snake_case!):

```json
{
  "count": 1,
  "counts_by_status": { "pending": 2, "acknowledged": 9, "escalated": 1, "skipped": 4 },
  "messages": [{
    "id": "5aa7bef8-…", "graph_message_id": "…", "graph_chat_id": "19:…@thread.v2",
    "chat_type": "oneOnOne",
    "sender": { "graph_user_id": "…", "display_name": "Jaydip (CTO)", "email": "…" },
    "is_from_me": false, "body": "Deploy before 5 PM", "importance": "normal",
    "mentions": [], "attachments": [], "reactions": [],
    "sent_at": "2026-07-25T12:00:00Z", "received_at": "2026-07-25T12:00:01Z",
    "acknowledgement": {
      "status": "escalated", "cooldown_seconds": 30, "due_at": "…",
      "acknowledged_at": null, "ack_source": null, "ack_detail": null,
      "escalated_at": "…", "checks": 1, "last_error": null
    }
  }]
}
```

`/profile` is also the source of the `employeeId` (its `email`) — fetch it right after login and
cache it.

### 6.9 Dev-only helpers (`ENVIRONMENT=development`) — auth required

| Method | Path | Purpose |
|--------|------|---------|
| `POST` | `/dev/push/test` | Fire a real push at your own devices, skipping the cooldown |
| `POST` | `/dev/messages/simulate` | Inject a fake Teams message and arm its cooldown |
| `POST` | `/dev/ack-tick` | Run one worker pass now instead of waiting |
| `POST` | `/dev/push/reload-credentials` | Re-read the Firebase key without restarting |

`POST /dev/push/test` body (all optional):
`{ "sender_name": "Jaydip (CTO)", "message_text": "test", "priority": "Critical", "message_id": "dev-test-0001" }`
→ its push carries `event: "dev.test"` and an unresolvable `messageId`.

---

## 7. The push payload — exact contract

Sent **data-only** (no `notification` block), Android `priority: high`, TTL **300s**.
Because there is no notification block, `onMessageReceived()` is called **in every app state**:
foreground, background, and (on most OEMs) after swipe-away. That is deliberate — a `notification`
payload would be eaten by the system tray and no full-screen alert would ever fire.

**Every value is a String** — FCM allows nothing else. Parse accordingly.

```json
{
  "event": "message.unacknowledged",
  "messageId": "5aa7bef8-b40e-4168-b919-4e161a49f2e7",
  "senderName": "Jaydip (CTO)",
  "priority": "Critical",
  "messageText": "Deploy before 5 PM please",
  "chatId": "19:…@thread.v2",
  "chatType": "oneOnOne",
  "reason": "cooldown_elapsed",
  "cooldownSeconds": "30",
  "sentAt": "2026-07-25T12:00:00+00:00",
  "receivedAt": "2026-07-25T12:00:01+00:00",
  "escalatedAt": "2026-07-25T12:00:31+00:00"
}
```

| Key | Meaning | App use |
|-----|---------|---------|
| `event` | `message.unacknowledged`, or `dev.test` from the test endpoint | Route the handler; ignore unknown events |
| `messageId` | Backend `messages.id` (UUID) | **Echo back verbatim** in the action call |
| `senderName` | Who sent the Teams message | Alert title |
| `priority` | `Critical` \| `High` \| `Normal` | Alert style (see below) |
| `messageText` | Message body, truncated at 2 KB | Alert body |
| `chatId` / `chatType` | Graph chat id, `oneOnOne`/`group` | "Open in Teams" deep link |
| `reason` | `cooldown_elapsed` or `graph_error` | Debug only |
| `cooldownSeconds` | `"30"` — string! | "Unanswered for 30s" label |
| `sentAt`/`receivedAt`/`escalatedAt` | ISO-8601 UTC | Timestamps on the alert |

Empty/null fields are **dropped** by the server before sending — never assume a key exists.
Always `data["senderName"] ?: "Unknown sender"`.

Priority mapping done server-side: Graph importance `high`/`urgent` → `Critical`; else any
`@mention` → `High`; else `Normal`.

Suggested app behaviour:

| `priority` | Behaviour |
|-----------|-----------|
| `Critical` | Full-screen activity over lockscreen, alarm sound, continuous vibration, bypass DND |
| `High` | Heads-up notification, sound + vibration |
| `Normal` | Standard notification |

**iOS note:** iOS targets additionally receive an APNS alert block (title = `senderName`,
body = `messageText`, `sound: default`, `content-available: 1`) because a data-only push cannot
reliably wake an iOS app. Register with `"platform": "ios"` to get it.

---

## 8. Exact call order, per lifecycle event

### 8.1 Cold start

```
1.  GET /health                                → connectivity + push_ready (optional, debug)
2.  read stored session token
3.  GET /auth/status  (Bearer)
    ├─ authenticated:false → Login screen → §8.2
    └─ authenticated:true  → GET /profile → cache email → §8.3
```

### 8.2 Login

```
1. Custom Tab → {BASE}/login
2. User signs in + consents on the Microsoft page
3. Deep link back: bitbrains://auth/callback?login=success&token=<JWT>
   (or ?login=error&message=… → show the message, back to the login screen)
4. Store token encrypted
5. GET /profile               → email, display name
6. POST /api/devices/register (Bearer + employeeId=email + current FCM token)
7. Assert linked == true, else show the "finish login" banner
```

### 8.3 Every launch when already logged in

```
1. FirebaseMessaging.getInstance().token
2. POST /api/devices/register   (idempotent — safe to send every launch)
3. GET /messages?ack_status=escalated   (if you build the list screen)
```

### 8.4 `onNewToken(token)` (FCM rotates tokens)

```
1. Store the new token locally
2. POST /api/devices/register with it
   └─ no session yet? queue it (WorkManager) and send after the next successful login
```
Skipping this is the second most common cause of "pushes stopped working".

### 8.5 Push received → `onMessageReceived(remoteMessage)`

```
1. data = remoteMessage.data;  if (data["event"] !in {"message.unacknowledged","dev.test"}) return
2. Persist it locally first (Room) — the process can die before the user reacts
3. priority == "Critical" ?  full-screen intent activity  :  heads-up notification
4. Actions on the alert: [Read]  [Ignore]  [Open in Teams]
```

### 8.6 User acts

```
Read      → POST /api/messages/{messageId}/action  { action:"READ",    employeeId, actionTimestamp, fcmToken }
Ignore    → POST /api/messages/{messageId}/action  { action:"IGNORED", … }
Swipe/timeout with no choice → send IGNORED too (silence is data)
Open in Teams → deep link, then send READ
```

Queue the call through WorkManager with retry — the phone may be offline exactly when the alert
fires. Mark the local row as synced only on a `200`.

### 8.7 Logout

```
1. POST /api/devices/unregister { fcmToken }
2. POST /logout   (Bearer)
3. Wipe the encrypted token, cached email, and local message rows
```

---

## 9. Suggested app file structure

```
app/src/main/java/com/bitcoding/bitbrains/
├── BitBrainsApp.kt                  Application: notification channel, DI
├── data/
│   ├── remote/
│   │   ├── ApiService.kt            Retrofit interface — every endpoint in §6
│   │   ├── ApiClient.kt             OkHttp: base URL, auth interceptor, logging, timeouts
│   │   ├── AuthInterceptor.kt       Adds "Authorization: Bearer …" when a token exists
│   │   └── dto/                     RegisterDeviceRequest/Response, MessageActionRequest/…
│   ├── local/
│   │   ├── SecureStore.kt           EncryptedSharedPreferences: session token, email, fcm token
│   │   ├── AlertDao.kt / AlertEntity.kt   Room: received alerts + sync state
│   │   └── AppDatabase.kt
│   └── repository/
│       ├── AuthRepository.kt        status / profile / logout
│       ├── DeviceRepository.kt      register / unregister / list
│       └── AlertRepository.kt       action reporting + local history
├── push/
│   ├── BitBrainsMessagingService.kt FirebaseMessagingService: onMessageReceived, onNewToken
│   ├── AlertNotifier.kt             Channel, full-screen intent, heads-up, action buttons
│   └── ActionReceiver.kt            BroadcastReceiver for the Read/Ignore buttons
├── work/
│   ├── RegisterDeviceWorker.kt      Retries registration until it succeeds
│   └── ReportActionWorker.kt        Retries READ/IGNORED until a 200
├── ui/
│   ├── login/LoginActivity.kt       Custom Tab launcher + permission prompts
│   ├── auth/AuthCallbackActivity.kt Deep-link handler: reads ?token=
│   ├── alert/FullScreenAlertActivity.kt  The alarm screen (Critical)
│   ├── home/HomeActivity.kt         Escalated list, profile, refresh
│   └── settings/DiagnosticsScreen.kt    /health, /api/devices, last push time
└── util/
    ├── PermissionHelper.kt          POST_NOTIFICATIONS, full-screen intent, battery opt
    └── DeepLinks.kt                 msteams://l/chat/{chatId} builder
```

---

## 10. Error handling rules

| Status | Meaning | App behaviour |
|--------|---------|---------------|
| `200` | Success | Continue. Still inspect `linked` / `resolved` / `acknowledged` in the body |
| `400` | Missing identity on the action call | Bug in the request — log, do **not** retry blindly |
| `401` | Session token missing/invalid/expired (7-day limit) | Clear the token, show the login screen |
| `404` | Unknown message / no active device | Show it, do not retry |
| `422` | Validation failed (blank or oversized field) | Fix locally, do not retry |
| `5xx` / timeout / offline | Backend or network | Retry with exponential backoff via WorkManager |

Errors come back shaped as `{ "status": "error", "detail": "…" }`.

Other rules:

- Timeouts: connect 10s, read 30s.
- Never block the UI on an API call from a push handler — persist locally, sync in the background.
- Never log the FCM token or the session token.
- Deduplicate by `messageId`: FCM can deliver the same push more than once.

---

## 11. Backend `.env` needed for mobile testing

Ask the backend team to confirm these before starting:

```
ENVIRONMENT=development           # exposes /dev/* helpers
FCM_ENABLED=true
FIREBASE_CREDENTIALS_FILE=bir-brains-firebase-adminsdk-fbsvc-a6d42ce360.json
FCM_TIME_TO_LIVE_SECONDS=300
ACK_COOLDOWN_SECONDS=30
FRONTEND_REDIRECT_URL=bitbrains://auth/callback   # for the mobile login flow
ISSUE_REDIRECT_TOKEN=true                          # required — this is how the app gets its token
PUBLIC_BASE_URL=https://<ngrok-id>.ngrok-free.app  # needed for real Teams messages
ALLOWED_ORIGINS=http://localhost:3000
```

Verify with `GET /health` → `"push_ready": true`. If it is `false`, `push_error` says why and
**no push will ever arrive** — stop and fix that first.

---

## 12. Testing checklist

Do them in this order; each one isolates a different failure.

1. `GET /health` → `push_ready: true`.
2. Login through the Custom Tab; deep link returns with `token`; `GET /auth/status` → `true`.
3. `POST /api/devices/register` → `linked: true`.
4. `GET /api/devices` → your device, `isActive: true`.
5. `POST /dev/push/test` → phone lights up within seconds. **Foreground first, then background,
   then locked screen, then after swiping the app away.** ← the real test
6. `POST /api/messages/{id}/action` with the test push's `messageId` → `200`,
   `resolved: false` (expected — the dev push has no real message).
7. `POST /dev/messages/simulate`, wait 35s → real escalation push arrives.
8. Send `READ` for it → `resolved: true, acknowledged: true`.
9. `GET /messages/{id}` → `acknowledgement.status: "acknowledged"`, `ack_source: "device"`.
10. Real Teams message from another account, ignore it 30s → push arrives.
11. Reply in Teams within 30s → **no** push (acknowledged by reply).
12. React with 👍 within 30s → **no** push (a reaction counts as acknowledgement).
13. Airplane mode during an alert → action is queued and syncs when back online.
14. Logout → `unregister` → `/dev/push/test` returns `404 no active device`.

---

## 13. Known gaps — raise these with the backend team

1. **Mobile deep-link login is not implemented server-side yet.** `FRONTEND_REDIRECT_URL` is one
   global value, so pointing it at `bitbrains://` breaks the web login. Needs a per-client
   redirect (e.g. `/login?client=mobile`).
2. **No token refresh endpoint.** The 7-day session token simply expires → full re-login.
   `POST /logout` cannot invalidate an already-issued bearer token.
3. **No unread/badge count endpoint.** Derive it from `GET /messages?ack_status=escalated`.
4. **`GET /messages` is snake_case while `/api/*` is camelCase.** Two DTO styles in one app —
   keep them in separate packages so nobody mixes them.
5. **The backend process must stay running** — Graph subscriptions expire in ≤60 minutes and are
   renewed by the in-process worker. If the API is down, no messages arrive at all.
6. **A registered-but-unlinked device is silent.** Always check `linked` and surface it in the UI;
   otherwise the user thinks the app works when it cannot receive anything.
