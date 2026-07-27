package com.bitcoding.bitbrains.push

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.Context
import android.content.Intent
import android.media.AudioAttributes
import android.media.RingtoneManager
import android.os.Build
import androidx.core.app.NotificationCompat
import androidx.core.app.NotificationManagerCompat
import com.bitcoding.bitbrains.R
import com.bitcoding.bitbrains.data.local.AlertEntity
import com.bitcoding.bitbrains.ui.alert.FullScreenAlertActivity

/**
 * Owns notification channels and turns an [AlertEntity] into the right surface
 * per priority (§7):
 *   Critical -> full-screen activity over the lockscreen, alarm sound, bypass DND
 *   High     -> heads-up notification with Read/Ignore/Open actions
 *   Normal   -> standard notification
 */
object AlertNotifier {

    // Bumped to _v2 because a channel's settings (importance, bypass-DND, sound)
    // can never be changed after creation (§4.3). An earlier build shipped a
    // weaker "critical_alerts", so we mint a new id to get bypass-DND + alarm sound.
    const val CHANNEL_CRITICAL = "critical_alerts_v2"
    private const val CHANNEL_CRITICAL_LEGACY = "critical_alerts"
    private const val CHANNEL_HIGH = "high_alerts"
    private const val CHANNEL_NORMAL = "normal_alerts"

    fun createChannels(context: Context) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.O) return
        val manager = context.getSystemService(NotificationManager::class.java)

        // Remove the stale channel from earlier installs so it doesn't linger in Settings.
        manager.deleteNotificationChannel(CHANNEL_CRITICAL_LEGACY)

        if (manager.getNotificationChannel(CHANNEL_CRITICAL) == null) {
            val alarmSound = RingtoneManager.getDefaultUri(RingtoneManager.TYPE_ALARM)
                ?: RingtoneManager.getDefaultUri(RingtoneManager.TYPE_NOTIFICATION)
            val channel = NotificationChannel(
                CHANNEL_CRITICAL,
                "Unanswered Teams messages",
                NotificationManager.IMPORTANCE_HIGH
            ).apply {
                description = "Full-screen alarm for messages you haven't acknowledged"
                enableVibration(true)
                setBypassDnd(true)
                lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                setSound(
                    alarmSound,
                    AudioAttributes.Builder().setUsage(AudioAttributes.USAGE_ALARM).build()
                )
            }
            manager.createNotificationChannel(channel)
        }

        if (manager.getNotificationChannel(CHANNEL_HIGH) == null) {
            manager.createNotificationChannel(
                NotificationChannel(CHANNEL_HIGH, "Important messages", NotificationManager.IMPORTANCE_HIGH).apply {
                    description = "Heads-up alerts for @mentions and important messages"
                    enableVibration(true)
                    lockscreenVisibility = Notification.VISIBILITY_PUBLIC
                }
            )
        }

        if (manager.getNotificationChannel(CHANNEL_NORMAL) == null) {
            manager.createNotificationChannel(
                NotificationChannel(CHANNEL_NORMAL, "Other messages", NotificationManager.IMPORTANCE_DEFAULT).apply {
                    description = "Standard notifications"
                }
            )
        }
    }

    fun notificationId(messageId: String): Int = messageId.hashCode()

    /**
     * Critical: show the full-screen alarm immediately, in EVERY device state.
     *
     * Two mechanisms, on purpose:
     *  1. A high-priority notification with setFullScreenIntent — the sanctioned
     *     path; reliably takes over when the screen is off/locked.
     *  2. A direct startActivity — so the alarm also appears over whatever app is
     *     in the foreground and when the screen is already on (where the OS would
     *     otherwise demote #1 to a heads-up banner). This needs the
     *     "Display over other apps" (SYSTEM_ALERT_WINDOW) background-activity-start
     *     exemption; if it's not granted the call is simply ignored and #1 stands.
     *
     * The activity is launchMode=singleInstance, so even if both fire there is
     * only ever one alarm screen.
     */
    fun showCritical(context: Context, alert: AlertEntity) {
        val fullScreenIntent = Intent(context, FullScreenAlertActivity::class.java).apply {
            flags = Intent.FLAG_ACTIVITY_NEW_TASK or Intent.FLAG_ACTIVITY_CLEAR_TOP
            putExtra(FullScreenAlertActivity.EXTRA_MESSAGE_ID, alert.messageId)
            putExtra(FullScreenAlertActivity.EXTRA_SENDER, alert.senderName)
            putExtra(FullScreenAlertActivity.EXTRA_PRIORITY, alert.priority)
            putExtra(FullScreenAlertActivity.EXTRA_TEXT, alert.messageText)
            putExtra(FullScreenAlertActivity.EXTRA_CHAT_ID, alert.chatId)
        }
        val id = notificationId(alert.messageId)
        val pending = PendingIntent.getActivity(
            context, id, fullScreenIntent,
            PendingIntent.FLAG_UPDATE_CURRENT or PendingIntent.FLAG_IMMUTABLE
        )

        val notification = NotificationCompat.Builder(context, CHANNEL_CRITICAL)
            .setSmallIcon(R.drawable.ic_alert)
            .setContentTitle(alert.senderName)
            .setContentText(alert.messageText)
            .setPriority(NotificationCompat.PRIORITY_MAX)
            .setCategory(NotificationCompat.CATEGORY_CALL)
            .setAutoCancel(false)
            .setOngoing(true)
            .setFullScreenIntent(pending, true)
            .setContentIntent(pending)
            .build()

        NotificationManagerCompat.from(context).notify(id, notification)

        // Launch directly too, so the alarm takes over even when another app is
        // open / the screen is already on. Harmless if the OS blocks it (no
        // overlay permission) — the full-screen-intent notification above remains.
        runCatching { context.startActivity(fullScreenIntent) }
    }

    /** High/Normal: heads-up or standard notification with inline actions. */
    fun showStandard(context: Context, alert: AlertEntity, high: Boolean) {
        val id = notificationId(alert.messageId)
        val channel = if (high) CHANNEL_HIGH else CHANNEL_NORMAL

        val builder = NotificationCompat.Builder(context, channel)
            .setSmallIcon(R.drawable.ic_alert)
            .setContentTitle(alert.senderName)
            .setContentText(alert.messageText)
            .setStyle(NotificationCompat.BigTextStyle().bigText(alert.messageText))
            .setPriority(if (high) NotificationCompat.PRIORITY_HIGH else NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(false)
            .addAction(0, "Read", ActionReceiver.pendingIntent(context, alert, "READ"))
            .addAction(0, "Ignore", ActionReceiver.pendingIntent(context, alert, "IGNORED"))

        if (!alert.chatId.isNullOrBlank()) {
            builder.addAction(0, "Open in Teams", ActionReceiver.pendingIntent(context, alert, "OPEN"))
        }

        NotificationManagerCompat.from(context).notify(id, builder.build())
    }

    fun cancel(context: Context, messageId: String) {
        NotificationManagerCompat.from(context).cancel(notificationId(messageId))
    }
}
