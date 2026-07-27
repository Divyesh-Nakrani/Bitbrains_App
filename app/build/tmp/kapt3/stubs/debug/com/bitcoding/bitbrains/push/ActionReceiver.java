package com.bitcoding.bitbrains.push;

/**
 * Handles the Read / Ignore / Open-in-Teams buttons on the heads-up
 * notification. Keeps zero network on the main thread — it only enqueues the
 * [ReportActionWorker] (WorkManager retries until a 200) and dismisses the
 * notification (§8.6, §10 "never block the UI on an API call from a push handler").
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000 \n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\u0018\u0000 \t2\u00020\u0001:\u0001\tB\u0005\u00a2\u0006\u0002\u0010\u0002J\u0018\u0010\u0003\u001a\u00020\u00042\u0006\u0010\u0005\u001a\u00020\u00062\u0006\u0010\u0007\u001a\u00020\bH\u0016\u00a8\u0006\n"}, d2 = {"Lcom/bitcoding/bitbrains/push/ActionReceiver;", "Landroid/content/BroadcastReceiver;", "()V", "onReceive", "", "context", "Landroid/content/Context;", "intent", "Landroid/content/Intent;", "Companion", "app_debug"})
public final class ActionReceiver extends android.content.BroadcastReceiver {
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "ActionReceiver";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_MESSAGE_ID = "messageId";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_ACTION = "action";
    @org.jetbrains.annotations.NotNull()
    public static final java.lang.String EXTRA_CHAT_ID = "chatId";
    @org.jetbrains.annotations.NotNull()
    public static final com.bitcoding.bitbrains.push.ActionReceiver.Companion Companion = null;
    
    public ActionReceiver() {
        super();
    }
    
    @java.lang.Override()
    public void onReceive(@org.jetbrains.annotations.NotNull()
    android.content.Context context, @org.jetbrains.annotations.NotNull()
    android.content.Intent intent) {
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000(\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0004\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u001e\u0010\b\u001a\u00020\t2\u0006\u0010\n\u001a\u00020\u000b2\u0006\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u0004R\u000e\u0010\u0003\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0086T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u000f"}, d2 = {"Lcom/bitcoding/bitbrains/push/ActionReceiver$Companion;", "", "()V", "EXTRA_ACTION", "", "EXTRA_CHAT_ID", "EXTRA_MESSAGE_ID", "TAG", "pendingIntent", "Landroid/app/PendingIntent;", "context", "Landroid/content/Context;", "alert", "Lcom/bitcoding/bitbrains/data/local/AlertEntity;", "action", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
        
        @org.jetbrains.annotations.NotNull()
        public final android.app.PendingIntent pendingIntent(@org.jetbrains.annotations.NotNull()
        android.content.Context context, @org.jetbrains.annotations.NotNull()
        com.bitcoding.bitbrains.data.local.AlertEntity alert, @org.jetbrains.annotations.NotNull()
        java.lang.String action) {
            return null;
        }
    }
}