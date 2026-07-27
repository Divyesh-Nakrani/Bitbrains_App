package com.bitcoding.bitbrains.push

import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import android.util.Log
import com.bitcoding.bitbrains.data.local.AlertEntity
import com.bitcoding.bitbrains.util.DeepLinks
import com.bitcoding.bitbrains.work.ReportActionWorker
import android.app.PendingIntent

/**
 * Handles the Read / Ignore / Open-in-Teams buttons on the heads-up
 * notification. Keeps zero network on the main thread — it only enqueues the
 * [ReportActionWorker] (WorkManager retries until a 200) and dismisses the
 * notification (§8.6, §10 "never block the UI on an API call from a push handler").
 */
class ActionReceiver : BroadcastReceiver() {

    override fun onReceive(context: Context, intent: Intent) {
        val messageId = intent.getStringExtra(EXTRA_MESSAGE_ID) ?: return
        val action = intent.getStringExtra(EXTRA_ACTION) ?: return
        val chatId = intent.getStringExtra(EXTRA_CHAT_ID)

        when (action) {
            "OPEN" -> {
                DeepLinks.teamsChat(chatId)?.let { uri ->
                    runCatching {
                        context.startActivity(
                            Intent(Intent.ACTION_VIEW, uri).addFlags(Intent.FLAG_ACTIVITY_NEW_TASK)
                        )
                    }.onFailure { Log.w(TAG, "Teams deep link failed: ${it.message}") }
                }
                // Opening the chat counts as reading it (§8.6).
                ReportActionWorker.enqueue(context, messageId, "READ")
            }
            else -> ReportActionWorker.enqueue(context, messageId, action)
        }

        AlertNotifier.cancel(context, messageId)
        // Collapse the notification shade so the button tap feels resolved.
        runCatching {
            @Suppress("DEPRECATION")
            context.sendBroadcast(Intent(Intent.ACTION_CLOSE_SYSTEM_DIALOGS))
        }
    }

    companion object {
        private const val TAG = "ActionReceiver"
        const val EXTRA_MESSAGE_ID = "messageId"
        const val EXTRA_ACTION = "action"
        const val EXTRA_CHAT_ID = "chatId"

        fun pendingIntent(context: Context, alert: AlertEntity, action: String): PendingIntent {
            val intent = Intent(context, ActionReceiver::class.java).apply {
                putExtra(EXTRA_MESSAGE_ID, alert.messageId)
                putExtra(EXTRA_ACTION, action)
                putExtra(EXTRA_CHAT_ID, alert.chatId)
            }
            val requestCode = alert.messageId.hashCode() * 31 + action.hashCode()
            return PendingIntent.getBroadcast(
                context, requestCode, intent,
                PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
            )
        }
    }
}
