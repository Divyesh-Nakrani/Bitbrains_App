# Teams Critical Alert — Android app

This is the Android piece of the hackathon project: a small app that receives a push
when a teammate's classifier/escalation service decides a message was missed, and
throws a full-screen, sound-and-vibration alert that stays up until the employee
taps **Mark as read** or **Ignore**.

It does not talk to Microsoft Teams or Graph directly — that's your teammates'
side. This app only talks to your shared backend over two REST calls, and to
Firebase Cloud Messaging (FCM) for push delivery.

## How it fits the overall workflow

```
Microsoft Teams → Graph subscription → 1:1 messages → LLM classification/escalation (teammates)
                                                              │
                                                              ▼
                                          Backend sends an FCM DATA message
                                                              │
                                                              ▼
                                            This Android app (AlertFirebaseMessagingService)
                                                              │
                                                              ▼
                                     Full-screen alert with sound, until user acts
                                                              │
                                                              ▼
                                        POST back to backend: READ or IGNORED
```

## One-time setup

1. **Firebase project:** `bir-brains`, Android app package name
   `com.bitcoding.bitbrains`. `app/google-services.json` is already in the repo,
   but its `api_key.current_key` is a placeholder (`PASTE_YOUR_REAL_API_KEY_HERE`).
   Download the real `google-services.json` from Firebase Console → Project
   settings → your Android app, and either replace the file entirely or paste
   the real key in. **Do not commit the real key to a public repo.**
2. **Point the app at your backend.** In `app/build.gradle.kts`, set:
   ```kotlin
   buildConfigField("String", "SERVER_BASE_URL", "\"https://your-backend.example.com/\"")
   ```
3. Open the project in Android Studio (Koala or newer) and run on a device or
   emulator with **Play Services** (a plain AVD without Play Services won't get FCM
   pushes reliably — use a Google APIs image).

## First run on the device

The app walks through a 4-step checklist on launch:

1. **Allow notifications** — the standard Android 13+ runtime permission.
2. **Allow full-screen alerts** — on Android 14+, this is only auto-granted to
   apps the system treats as calling/alarm apps. Everyone else (that's us) has
   to send the user to `Settings.ACTION_MANAGE_APP_USE_FULL_SCREEN_INTENT` once.
   Below Android 14 this step is a no-op — it's already allowed.
3. **Disable battery optimization** (recommended) — otherwise some OEMs (Xiaomi,
   Samsung, OnePlus especially) throttle the background FCM listener and delay
   alerts by minutes.
4. **Register this device** — enter an employee ID, the app fetches the FCM
   token and POSTs both to the backend.

## The API contract your backend needs to implement

These two endpoints are the entire interface between this app and the rest of
the system (see `network/ApiService.kt` for the Retrofit definitions):

### 1. Register / refresh a device token

```
POST /api/devices/register
Content-Type: application/json

{
  "employeeId": "emp_1234",
  "fcmToken": "f7Xk2...long-token-string",
  "platform": "android"
}
```
Called once at first registration, and again automatically whenever FCM rotates
the token (`onNewToken`). The backend should **upsert** by employeeId — always
keep the latest token, since old ones stop working silently.

Response (any 2xx is treated as success):
```json
{ "success": true }
```

### 2. Report what the employee did with an alert

```
POST /api/messages/{messageId}/action
Content-Type: application/json

{
  "employeeId": "emp_1234",
  "action": "READ",
  "actionTimestamp": 1732541234000
}
```
`action` is either `"READ"` or `"IGNORED"`. `messageId` in the URL must be the
exact same id the backend used when it sent the FCM push (see below) — that's
how the backend knows which escalation to close out.

### 3. What the backend must send via FCM (this app's input)

**Send it as an FCM data message, not a notification message.** Data messages
are the only kind guaranteed to reach `onMessageReceived()` in the background —
notification messages get shown by the OS directly and skip our custom
full-screen handling.

```json
{
  "message": {
    "token": "<the fcmToken registered for this employee>",
    "data": {
      "messageId": "msg_98765",
      "senderName": "Jordan (Acme Corp)",
      "priority": "Critical",
      "messageText": "Can you approve the deployment before 5 PM? Blocking release."
    }
  }
}
```
Sent via the FCM HTTP v1 API (`POST https://fcm.googleapis.com/v1/projects/<project-id>/messages:send`,
authenticated with a service account). Quick manual test option: Firebase console
→ Cloud Messaging → "Send test message" supports data-only payloads too, or use `curl`
with a service-account OAuth token if your backend isn't ready yet — either way, just
match the four `data` keys above exactly, since `AlertFirebaseMessagingService.kt`
reads them by name.

## Known limitations (by design, for the hackathon)

- **Android only**, per your team's decision — iOS would need a different
  approach (Time-Sensitive notifications, no true full-screen takeover; see the
  BRD if you want the detail later).
- If the network call in step 2 fails (no signal), the app still dismisses the
  alert locally so the employee isn't stuck — there's no retry queue. Fine for a
  demo; flag it as a follow-up if this goes further.
- No login/SSO — employee ID is typed in manually once. Swap for real auth later.
- The full-screen intent reliably launches when the phone is **locked**. When the
  phone is already unlocked and the app is backgrounded, most OEMs show it as a
  heads-up banner instead of a true takeover — that's an OS-level behavior, not
  a bug in this code. Worth calling out during the demo.

## Project structure

```
app/src/main/java/com/bitcoding/bitbrains/
├── MainActivity.kt                  → permission checklist + device registration
├── fcm/AlertFirebaseMessagingService.kt → receives the push, triggers the alert
├── ui/FullScreenAlertActivity.kt    → the lock-screen-style alert UI
├── network/ApiService.kt            → the 2 endpoints above (Retrofit)
├── network/ApiClient.kt             → Retrofit client, reads SERVER_BASE_URL
├── network/Models.kt                → request/response data classes
└── util/
    ├── NotificationHelper.kt        → notification channel + full-screen intent notification
    ├── AlertSoundPlayer.kt          → looping sound + vibration until dismissed
    └── PrefsManager.kt              → local storage for employeeId / token
```
