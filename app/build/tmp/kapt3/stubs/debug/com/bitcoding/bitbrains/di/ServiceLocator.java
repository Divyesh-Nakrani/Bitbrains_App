package com.bitcoding.bitbrains.di;

/**
 * Tiny manual DI. Everything is lazily built off the application context and
 * lives for the process lifetime, so Workers, the FCM service, and Activities
 * all share one SecureStore / ApiClient / Room instance.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000F\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u000b\n\u0002\u0018\u0002\n\u0002\b\u0004\n\u0002\u0010\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u000e\u0010\u001a\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dJ\u000e\u0010\u001e\u001a\u00020\u001b2\u0006\u0010\u001c\u001a\u00020\u001dR\u001e\u0010\u0005\u001a\u00020\u00042\u0006\u0010\u0003\u001a\u00020\u0004@BX\u0086.\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0006\u0010\u0007R\u001e\u0010\t\u001a\u00020\b2\u0006\u0010\u0003\u001a\u00020\b@BX\u0086.\u00a2\u0006\b\n\u0000\u001a\u0004\b\n\u0010\u000bR\u001e\u0010\r\u001a\u00020\f2\u0006\u0010\u0003\u001a\u00020\f@BX\u0086.\u00a2\u0006\b\n\u0000\u001a\u0004\b\u000e\u0010\u000fR\u001e\u0010\u0011\u001a\u00020\u00102\u0006\u0010\u0003\u001a\u00020\u0010@BX\u0086.\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0012\u0010\u0013R\u000e\u0010\u0014\u001a\u00020\u0015X\u0082\u000e\u00a2\u0006\u0002\n\u0000R\u001e\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u0003\u001a\u00020\u0016@BX\u0086.\u00a2\u0006\b\n\u0000\u001a\u0004\b\u0018\u0010\u0019\u00a8\u0006\u001f"}, d2 = {"Lcom/bitcoding/bitbrains/di/ServiceLocator;", "", "()V", "<set-?>", "Lcom/bitcoding/bitbrains/data/repository/AlertRepository;", "alertRepository", "getAlertRepository", "()Lcom/bitcoding/bitbrains/data/repository/AlertRepository;", "Lcom/bitcoding/bitbrains/data/remote/ApiClient;", "apiClient", "getApiClient", "()Lcom/bitcoding/bitbrains/data/remote/ApiClient;", "Lcom/bitcoding/bitbrains/data/repository/AuthRepository;", "authRepository", "getAuthRepository", "()Lcom/bitcoding/bitbrains/data/repository/AuthRepository;", "Lcom/bitcoding/bitbrains/data/repository/DeviceRepository;", "deviceRepository", "getDeviceRepository", "()Lcom/bitcoding/bitbrains/data/repository/DeviceRepository;", "initialized", "", "Lcom/bitcoding/bitbrains/data/local/SecureStore;", "secureStore", "getSecureStore", "()Lcom/bitcoding/bitbrains/data/local/SecureStore;", "ensure", "", "context", "Landroid/content/Context;", "init", "app_debug"})
public final class ServiceLocator {
    @kotlin.jvm.Volatile()
    private static volatile boolean initialized = false;
    private static com.bitcoding.bitbrains.data.local.SecureStore secureStore;
    private static com.bitcoding.bitbrains.data.remote.ApiClient apiClient;
    private static com.bitcoding.bitbrains.data.repository.AuthRepository authRepository;
    private static com.bitcoding.bitbrains.data.repository.DeviceRepository deviceRepository;
    private static com.bitcoding.bitbrains.data.repository.AlertRepository alertRepository;
    @org.jetbrains.annotations.NotNull()
    public static final com.bitcoding.bitbrains.di.ServiceLocator INSTANCE = null;
    
    private ServiceLocator() {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bitcoding.bitbrains.data.local.SecureStore getSecureStore() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bitcoding.bitbrains.data.remote.ApiClient getApiClient() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bitcoding.bitbrains.data.repository.AuthRepository getAuthRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bitcoding.bitbrains.data.repository.DeviceRepository getDeviceRepository() {
        return null;
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bitcoding.bitbrains.data.repository.AlertRepository getAlertRepository() {
        return null;
    }
    
    public final void init(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
    
    /**
     * Safe accessor for entry points (Workers) that may run before Application.onCreate.
     */
    public final void ensure(@org.jetbrains.annotations.NotNull()
    android.content.Context context) {
    }
}