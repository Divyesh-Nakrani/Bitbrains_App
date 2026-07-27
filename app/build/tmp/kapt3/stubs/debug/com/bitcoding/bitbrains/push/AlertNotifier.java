package com.bitcoding.bitbrains.push;

/**
 * Owns notification channels and turns an [AlertEntity] into the right surface
 * per priority (§7):
 *  Critical -> full-screen activity over the lockscreen, alarm sound, bypass DND
 *  High     -> heads-up notification with Read/Ignore/Open actions
 *  Normal   -> standard notification
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00008\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0016\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\u0004J\u000e\u0010\r\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000bJ\u000e\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\f\u001a\u00020\u0004J\u0016\u0010\u0010\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u0012J\u001e\u0010\u0013\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\u0011\u001a\u00020\u00122\u0006\u0010\u0014\u001a\u00020\u0015R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/bitcoding/bitbrains/push/AlertNotifier;", "", "()V", "CHANNEL_CRITICAL", "", "CHANNEL_CRITICAL_LEGACY", "CHANNEL_HIGH", "CHANNEL_NORMAL", "cancel", "", "context", "Landroid/content/Context;", "messageId", "createChannels", "notificationId", "", "showCritical", "alert", "Lcom/bitcoding/bitbrains/data/local/AlertEntity;", "showStandard", "high", "", "app_debug"})
public final class AlertNotifier {
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String CHANNEL_CRITICAL = "critical_alerts_v2";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_CRITICAL_LEGACY = "critical_alerts";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_HIGH = "high_alerts";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String CHANNEL_NORMAL = "normal_alerts";
    @org.jetbrains.annotations.NotNull()
    public static final com.bitcoding.bitbrains.push.AlertNotifier INSTANCE = null;
    
    private AlertNotifier() {
        super();
    }
    
    public final void createChannels(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    public final int notificationId(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId) {
        return 0;
    }
    
    /**
     * Critical: show the full-screen alarm immediately, in EVERY device state.
     *
     * Two mechanisms, on purpose:
     * 1. A high-priority notification with setFullScreenIntent — the sanctioned
     *    path; reliably takes over when the screen is off/locked.
     * 2. A direct startActivity — so the alarm also appears over whatever app is
     *    in the foreground and when the screen is already on (where the OS would
     *    otherwise demote #1 to a heads-up banner). This needs the
     *    "Display over other apps" (SYSTEM_ALERT_WINDOW) background-activity-start
     *    exemption; if it's not granted the call is simply ignored and #1 stands.
     *
     * The activity is launchMode=singleInstance, so even if both fire there is
     * only ever one alarm screen.
     */
    public final void showCritical(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.bitcoding.bitbrains.data.local.AlertEntity alert) {
    }
    
    /**
     * High/Normal: heads-up or standard notification with inline actions.
     */
    public final void showStandard(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    com.bitcoding.bitbrains.data.local.AlertEntity alert, boolean high) {
    }
    
    public final void cancel(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    java.lang.String messageId) {
    }
}