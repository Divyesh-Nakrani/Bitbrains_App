package com.bitcoding.bitbrains.util

import android.app.NotificationManager
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.os.PowerManager
import android.provider.Settings
import androidx.core.content.ContextCompat

/**
 * All the runtime setup the app must do on first launch (§4.2). Each check is
 * pure state; each "request" launches the relevant system screen/dialog.
 */
object PermissionHelper {

    fun hasNotificationPermission(context: Context): Boolean =
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU) {
            ContextCompat.checkSelfPermission(
                context, android.Manifest.permission.POST_NOTIFICATIONS
            ) == PackageManager.PERMISSION_GRANTED
        } else true

    fun canUseFullScreenIntent(context: Context): Boolean =
        if (Build.VERSION.SDK_INT >= 34) {
            context.getSystemService(NotificationManager::class.java).canUseFullScreenIntent()
        } else true

    fun isIgnoringBatteryOptimizations(context: Context): Boolean {
        val pm = context.getSystemService(Context.POWER_SERVICE) as PowerManager
        return pm.isIgnoringBatteryOptimizations(context.packageName)
    }

    /**
     * Android 14+: USE_FULL_SCREEN_INTENT is only auto-granted to calling/alarm
     * apps. Everyone else is sent here once to switch it on (§4.2 item 2).
     */
    fun fullScreenIntentSettings(context: Context): Intent? {
        if (Build.VERSION.SDK_INT < 34) return null
        return Intent(Settings.ACTION_MANAGE_APP_USE_FULL_SCREEN_INTENT).apply {
            data = Uri.parse("package:${context.packageName}")
        }
    }

    /** Doze delays alerts on OEM ROMs unless the app is exempted (§4.2 item 3). */
    fun batteryOptimizationSettings(context: Context): Intent =
        Intent(Settings.ACTION_REQUEST_IGNORE_BATTERY_OPTIMIZATIONS).apply {
            data = Uri.parse("package:${context.packageName}")
        }

    /**
     * "Display over other apps" (SYSTEM_ALERT_WINDOW). This is what lets the
     * Critical alarm screen launch immediately over any foreground app / from the
     * background, instead of the OS demoting it to a heads-up banner when the
     * screen is already on. Without it we fall back to the full-screen intent,
     * which only reliably takes over on a locked screen.
     */
    fun canDrawOverlays(context: Context): Boolean = Settings.canDrawOverlays(context)

    fun overlaySettings(context: Context): Intent =
        Intent(Settings.ACTION_MANAGE_OVERLAY_PERMISSION).apply {
            data = Uri.parse("package:${context.packageName}")
        }
}
