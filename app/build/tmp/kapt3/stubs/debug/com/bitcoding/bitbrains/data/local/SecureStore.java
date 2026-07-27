package com.bitcoding.bitbrains.data.local;

/**
 * Encrypted storage for everything sensitive: the backend session token, the
 * user's Microsoft email (our [employeeId]), and the current FCM token.
 *
 * MOBILE_APP_INTEGRATION.md §5: "Storage: EncryptedSharedPreferences. Never
 * plain SharedPreferences, never logs." The Microsoft access/refresh tokens
 * never leave the server — the app only ever holds the backend's own bearer.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000>\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u000b\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\t\n\u0002\b\n\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u0000 (2\u00020\u0001:\u0001(B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004J\u0006\u0010#\u001a\u00020$J\u001e\u0010%\u001a\u00020&*\u00020&2\u0006\u0010\'\u001a\u00020\u00062\b\u0010\t\u001a\u0004\u0018\u00010\u0006H\u0002R\u0011\u0010\u0005\u001a\u00020\u00068F\u00a2\u0006\u0006\u001a\u0004\b\u0007\u0010\bR(\u0010\n\u001a\u0004\u0018\u00010\u00062\b\u0010\t\u001a\u0004\u0018\u00010\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u000b\u0010\b\"\u0004\b\f\u0010\rR(\u0010\u000e\u001a\u0004\u0018\u00010\u00062\b\u0010\t\u001a\u0004\u0018\u00010\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u000f\u0010\b\"\u0004\b\u0010\u0010\rR\u0011\u0010\u0011\u001a\u00020\u00128F\u00a2\u0006\u0006\u001a\u0004\b\u0011\u0010\u0013R$\u0010\u0015\u001a\u00020\u00142\u0006\u0010\t\u001a\u00020\u00148F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u0016\u0010\u0017\"\u0004\b\u0018\u0010\u0019R$\u0010\u001a\u001a\u00020\u00122\u0006\u0010\t\u001a\u00020\u00128F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b\u001b\u0010\u0013\"\u0004\b\u001c\u0010\u001dR\u000e\u0010\u001e\u001a\u00020\u001fX\u0082\u0004\u00a2\u0006\u0002\n\u0000R(\u0010 \u001a\u0004\u0018\u00010\u00062\b\u0010\t\u001a\u0004\u0018\u00010\u00068F@FX\u0086\u000e\u00a2\u0006\f\u001a\u0004\b!\u0010\b\"\u0004\b\"\u0010\r\u00a8\u0006)"}, d2 = {"Lcom/bitcoding/bitbrains/data/local/SecureStore;", "", "context", "Landroid/content/Context;", "(Landroid/content/Context;)V", "deviceId", "", "getDeviceId", "()Ljava/lang/String;", "value", "email", "getEmail", "setEmail", "(Ljava/lang/String;)V", "fcmToken", "getFcmToken", "setFcmToken", "isLoggedIn", "", "()Z", "", "lastPushAt", "getLastPushAt", "()J", "setLastPushAt", "(J)V", "linked", "getLinked", "setLinked", "(Z)V", "prefs", "Landroid/content/SharedPreferences;", "sessionToken", "getSessionToken", "setSessionToken", "clearSession", "", "putStringOrRemove", "Landroid/content/SharedPreferences$Editor;", "key", "Companion", "app_debug"})
public final class SecureStore {
    @org.jetbrains.annotations.NotNull()
    private final android.content.SharedPreferences prefs = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_TOKEN = "session_token";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_EMAIL = "email";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_LINKED = "linked";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_FCM = "fcm_token";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_DEVICE_ID = "device_id";
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String KEY_LAST_PUSH = "last_push_at";
    @org.jetbrains.annotations.NotNull()
    public static final com.bitcoding.bitbrains.data.local.SecureStore.Companion Companion = null;
    
    public SecureStore(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
        super();
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getSessionToken() {
        return null;
    }
    
    public final void setSessionToken(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    public final boolean isLoggedIn() {
        return false;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getEmail() {
        return null;
    }
    
    public final void setEmail(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    public final boolean getLinked() {
        return false;
    }
    
    public final void setLinked(boolean value) {
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.String getFcmToken() {
        return null;
    }
    
    public final void setFcmToken(@org.jetbrains.annotations.Nullable()
    java.lang.String value) {
    }
    
    @org.jetbrains.annotations.NotNull()
    public final java.lang.String getDeviceId() {
        return null;
    }
    
    public final long getLastPushAt() {
        return 0L;
    }
    
    public final void setLastPushAt(long value) {
    }
    
    /**
     * Wipe everything on logout (§8.7).
     */
    public final void clearSession() {
    }
    
    private final android.content.SharedPreferences.Editor putStringOrRemove(android.content.SharedPreferences.Editor $this$putStringOrRemove, java.lang.String key, java.lang.String value) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0014\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0006\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0005\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\b\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\n"}, d2 = {"Lcom/bitcoding/bitbrains/data/local/SecureStore$Companion;", "", "()V", "KEY_DEVICE_ID", "", "KEY_EMAIL", "KEY_FCM", "KEY_LAST_PUSH", "KEY_LINKED", "KEY_TOKEN", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}