package com.bitcoding.bitbrains.push

import android.util.Log
import com.bitcoding.bitbrains.data.local.AlertEntity
import com.bitcoding.bitbrains.di.ServiceLocator
import com.bitcoding.bitbrains.work.RegisterDeviceWorker
import com.google.firebase.messaging.FirebaseMessagingService
import com.google.firebase.messaging.RemoteMessage
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.runBlocking

/**
 * FCM entry point. Pushes are data-only (§7), so this fires in EVERY app state —
 * foreground, background, and after swipe-away. That is exactly why the backend
 * never sends a notification block: a system-tray notification would swallow the
 * full-screen alert.
 */
class BitBrainsMessagingService : FirebaseMessagingService() {

    // §8.4 — a rotated token must be re-registered, or pushes silently stop.
    override fun onNewToken(token: String) {
        super.onNewToken(token)
        Log.d(TAG, "New FCM token")
        ServiceLocator.ensure(applicationContext)
        ServiceLocator.secureStore.fcmToken = token
        // If there's no session/email yet, the Worker no-ops and we re-enqueue
        // after login — WorkManager guarantees the eventual send.
        RegisterDeviceWorker.enqueue(applicationContext)
    }

    override fun onMessageReceived(remoteMessage: RemoteMessage) {
        super.onMessageReceived(remoteMessage)
        ServiceLocator.ensure(applicationContext)

        val data = remoteMessage.data
        val event = data["event"] ?: return
        if (event !in KNOWN_EVENTS) {
            Log.d(TAG, "Ignoring unknown event: $event")
            return
        }
        val messageId = data["messageId"] ?: return

        // Every value is a String; keys with empty/null values are dropped
        // server-side, so always default (§7).
        val alert = AlertEntity(
            messageId = messageId,
            event = event,
            senderName = data["senderName"] ?: "Unknown sender",
            priority = data["priority"] ?: "Normal",
            messageText = data["messageText"] ?: "",
            chatId = data["chatId"],
            chatType = data["chatType"],
            cooldownSeconds = data["cooldownSeconds"],
            sentAt = data["sentAt"],
            receivedAt = data["receivedAt"],
            escalatedAt = data["escalatedAt"],
            receivedAtLocal = System.currentTimeMillis()
        )

        // Persist first — the process can die before the user reacts (§8.5 step 2).
        // insert() returns -1 on a duplicate messageId (FCM re-delivery); skip
        // re-alerting in that case (§10 dedup rule).
        val isNew = runBlocking(Dispatchers.IO) {
            ServiceLocator.alertRepository.persist(alert)
        }
        ServiceLocator.secureStore.lastPushAt = alert.receivedAtLocal
        if (!isNew) {
            Log.d(TAG, "Duplicate push for $messageId — skipping")
            return
        }

        when (alert.priority.uppercase()) {
            "CRITICAL" -> AlertNotifier.showCritical(applicationContext, alert)
            "HIGH" -> AlertNotifier.showStandard(applicationContext, alert, high = true)
            else -> AlertNotifier.showStandard(applicationContext, alert, high = false)
        }
    }

    companion object {
        private const val TAG = "BitBrainsFCM"
        private val KNOWN_EVENTS = setOf("message.unacknowledged", "dev.test")
    }
}
