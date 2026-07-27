package com.bitcoding.bitbrains.ui.home;

/**
 * The dashboard (§8.3). Reached only when a session exists, so it doubles as the
 * "authenticated with Teams" confirmation screen. On every resume it re-validates
 * the session, refreshes the FCM token + registration (idempotent), surfaces the
 * linked state, loads the escalated-message list, and reflects the live
 * alert-permission state (managed inline here rather than back on the login screen).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\u0010\u000e\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0007\n\u0002\u0018\u0002\n\u0002\b\n\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J \u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000f2\u0006\u0010\u0010\u001a\u00020\u00112\u0006\u0010\u0012\u001a\u00020\u0013H\u0002J\b\u0010\u0014\u001a\u00020\rH\u0002J\b\u0010\u0015\u001a\u00020\rH\u0002J\u000e\u0010\u0016\u001a\u00020\rH\u0082@\u00a2\u0006\u0002\u0010\u0017J\b\u0010\u0018\u001a\u00020\rH\u0002J\u0012\u0010\u0019\u001a\u00020\r2\b\u0010\u001a\u001a\u0004\u0018\u00010\u001bH\u0014J\b\u0010\u001c\u001a\u00020\rH\u0014J\b\u0010\u001d\u001a\u00020\rH\u0002J\b\u0010\u001e\u001a\u00020\rH\u0002J\b\u0010\u001f\u001a\u00020\rH\u0002J\b\u0010 \u001a\u00020\rH\u0002J\b\u0010\u0005\u001a\u00020\rH\u0002J\u0010\u0010!\u001a\u00020\r2\u0006\u0010\"\u001a\u00020\u0007H\u0002J\b\u0010#\u001a\u00020\rH\u0002J\b\u0010$\u001a\u00020\rH\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000R\u0014\u0010\u0005\u001a\b\u0012\u0004\u0012\u00020\u00070\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0014\u0010\b\u001a\u00020\t8BX\u0082\u0004\u00a2\u0006\u0006\u001a\u0004\b\n\u0010\u000b\u00a8\u0006%"}, d2 = {"Lcom/bitcoding/bitbrains/ui/home/HomeActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/bitcoding/bitbrains/databinding/ActivityHomeBinding;", "requestNotifications", "Landroidx/activity/result/ActivityResultLauncher;", "", "store", "Lcom/bitcoding/bitbrains/data/local/SecureStore;", "getStore", "()Lcom/bitcoding/bitbrains/data/local/SecureStore;", "bindPerm", "", "granted", "", "status", "Landroid/widget/TextView;", "button", "Landroid/widget/Button;", "ensureFcmTokenThenRegister", "goLogin", "loadEscalated", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "logout", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "openBatterySettings", "openFullScreenSettings", "openOverlaySettings", "refresh", "toast", "msg", "updatePermissions", "updateStatus", "app_debug"})
public final class HomeActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.bitcoding.bitbrains.databinding.ActivityHomeBinding binding;
    @org.jetbrains.annotations.NotNull()
    private final androidx.activity.result.ActivityResultLauncher<java.lang.String> requestNotifications = null;
    
    public HomeActivity() {
        super();
    }
    
    private final com.bitcoding.bitbrains.data.local.SecureStore getStore() {
        return null;
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    private final void refresh() {
    }
    
    private final void ensureFcmTokenThenRegister() {
    }
    
    private final java.lang.Object loadEscalated(kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    private final void updateStatus() {
    }
    
    private final void updatePermissions() {
    }
    
    private final void bindPerm(boolean granted, android.widget.TextView status, android.widget.Button button) {
    }
    
    private final void requestNotifications() {
    }
    
    private final void openFullScreenSettings() {
    }
    
    private final void openBatterySettings() {
    }
    
    private final void openOverlaySettings() {
    }
    
    private final void logout() {
    }
    
    private final void goLogin() {
    }
    
    private final void toast(java.lang.String msg) {
    }
}