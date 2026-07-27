package com.bitcoding.bitbrains.ui.login;

/**
 * Launcher / sign-in screen. This screen — and the "Sign in with Microsoft"
 * button — is only ever shown when there is NO session. If a token already
 * exists we jump straight to the dashboard, which surfaces the authenticated
 * state (Home re-validates the token and bounces back here on a 401).
 *
 * Alert permissions are handled on the dashboard after sign-in, so this screen
 * stays focused on the one action a signed-out user can take.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\"\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\u0005\u00a2\u0006\u0002\u0010\u0002J\b\u0010\u0005\u001a\u00020\u0006H\u0002J\u0012\u0010\u0007\u001a\u00020\u00062\b\u0010\b\u001a\u0004\u0018\u00010\tH\u0014J\b\u0010\n\u001a\u00020\u0006H\u0014J\b\u0010\u000b\u001a\u00020\u0006H\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082.\u00a2\u0006\u0002\n\u0000\u00a8\u0006\f"}, d2 = {"Lcom/bitcoding/bitbrains/ui/login/LoginActivity;", "Landroidx/appcompat/app/AppCompatActivity;", "()V", "binding", "Lcom/bitcoding/bitbrains/databinding/ActivityLoginBinding;", "goHome", "", "onCreate", "savedInstanceState", "Landroid/os/Bundle;", "onResume", "startMicrosoftLogin", "app_debug"})
public final class LoginActivity extends androidx.appcompat.app.AppCompatActivity {
    private com.bitcoding.bitbrains.databinding.ActivityLoginBinding binding;
    
    public LoginActivity() {
        super();
    }
    
    @java.lang.Override()
    protected void onCreate(@org.jetbrains.annotations.Nullable()
    android.os.Bundle savedInstanceState) {
    }
    
    @java.lang.Override()
    protected void onResume() {
    }
    
    /**
     * §6.3 — /login is a 302 to Microsoft; open it in a Custom Tab, never Retrofit.
     */
    private final void startMicrosoftLogin() {
    }
    
    private final void goHome() {
    }
}