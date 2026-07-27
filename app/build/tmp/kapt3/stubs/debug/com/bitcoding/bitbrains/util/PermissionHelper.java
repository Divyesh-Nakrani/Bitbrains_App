package com.bitcoding.bitbrains.util;

/**
 * All the runtime setup the app must do on first launch (§4.2). Each check is
 * pure state; each "request" launches the relevant system screen/dialog.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0002\b\u0006\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u0007\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\t\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u0010\u0010\n\u001a\u0004\u0018\u00010\u00042\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\u000b\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\f\u001a\u00020\b2\u0006\u0010\u0005\u001a\u00020\u0006J\u000e\u0010\r\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u0006\u00a8\u0006\u000e"}, d2 = {"Lcom/bitcoding/bitbrains/util/PermissionHelper;", "", "()V", "batteryOptimizationSettings", "Landroid/content/Intent;", "context", "Landroid/content/Context;", "canDrawOverlays", "", "canUseFullScreenIntent", "fullScreenIntentSettings", "hasNotificationPermission", "isIgnoringBatteryOptimizations", "overlaySettings", "app_debug"})
public final class PermissionHelper {
    @org.jetbrains.annotations.NotNull()
    public static final com.bitcoding.bitbrains.util.PermissionHelper INSTANCE = null;
    
    private PermissionHelper() {
        super();
    }
    
    public final boolean hasNotificationPermission(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final boolean canUseFullScreenIntent(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    public final boolean isIgnoringBatteryOptimizations(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    /**
     * Android 14+: USE_FULL_SCREEN_INTENT is only auto-granted to calling/alarm
     * apps. Everyone else is sent here once to switch it on (§4.2 item 2).
     */
    @org.jetbrains.annotations.Nullable()
    public final android.content.Intent fullScreenIntentSettings(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    /**
     * Doze delays alerts on OEM ROMs unless the app is exempted (§4.2 item 3).
     */
    @org.jetbrains.annotations.NotNull()
    public final android.content.Intent batteryOptimizationSettings(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
    
    /**
     * "Display over other apps" (SYSTEM_ALERT_WINDOW). This is what lets the
     * Critical alarm screen launch immediately over any foreground app / from the
     * background, instead of the OS demoting it to a heads-up banner when the
     * screen is already on. Without it we fall back to the full-screen intent,
     * which only reliably takes over on a locked screen.
     */
    public final boolean canDrawOverlays(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return false;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final android.content.Intent overlaySettings(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        return null;
    }
}