package com.bitcoding.bitbrains.ui.alert;

/**
 * The Critical alarm screen (§7). Appears over the lockscreen, wakes the display,
 * loops an alarm sound + vibration until the user chooses. Read/Ignore are
 * reported through [ReportActionWorker] so the call survives being offline (§8.6).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0007\u0018\u0000 \u00152\u00020\u0001:\u0001\u0015B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u000b\u001a\u00020\fH\u0017J\u0012\u0010\r\u001a\u00020\f2\b\u0010\u000e\u001a\u0004\u0018\u00010\u000fH\u0014J\b\u0010\u0010\u001a\u00020\fH\u0014J\b\u0010\u0011\u001a\u00020\fH\u0014J\u0010\u0010\u0012\u001a\u00020\f2\u0006\u0010\u0013\u001a\u00020\u0006H\u0002J\b\u0010\u0014\u001a\u00020\fH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0016"}, d2 = {"Lcom/bitcoding/bitbrains/ui/alert/FullScreenAlertActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/bitcoding/bitbrains/databinding/ActivityFullScreenAlertBinding;", "messageId", "", "resolved", "", "soundPlayer", "Lcom/bitcoding/bitbrains/util/AlertSoundPlayer;", "onBackPressed", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onDestroy", "onUserLeaveHint", "resolve", "action", "showOverLockScreen", "Companion", "app_debug"})
public final class FullScreenAlertActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.bitcoding.bitbrains.databinding.ActivityFullScreenAlertBinding binding;
    private com.bitcoding.bitbrains.util.AlertSoundPlayer soundPlayer;
    @org.jetbrains.annotations.NotNull()
    private java.lang.String messageId = "";
    private boolean resolved = false;
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_MESSAGE_ID = "extra_message_id";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_SENDER = "extra_sender";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_PRIORITY = "extra_priority";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_TEXT = "extra_text";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_CHAT_ID = "extra_chat_id";
    @org.jetbrains.annotations.NotNull()
    public static final com.bitcoding.bitbrains.ui.alert.FullScreenAlertActivity.Companion Companion = null;
    
    public FullScreenAlertActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    private final void showOverLockScreen() {
    }
    
    private final void resolve(java.lang.String action) {
    }
    
    @java.lang.Override()
    protected void onDestroy() {
    }
    
    @java.lang.Override()
    protected void onUserLeaveHint() {
    }
    
    @java.lang.Override()
    @java.lang.Deprecated()
    public void onBackPressed() {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0005\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\t"}, d2 = {"Lcom/bitcoding/bitbrains/ui/alert/FullScreenAlertActivity$Companion;", "", "()V", "EXTRA_CHAT_ID", "", "EXTRA_MESSAGE_ID", "EXTRA_PRIORITY", "EXTRA_SENDER", "EXTRA_TEXT", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}