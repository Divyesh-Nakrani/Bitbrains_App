package com.bitcoding.bitbrains.data.repository;

/**
 * Device registration / unregistration / listing (§6.4-6.6).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u00004\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000b\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u00002\u00020\u0001B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u0010\u0010\u0007\u001a\u0004\u0018\u00010\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0010\u0010\n\u001a\u0004\u0018\u00010\u000bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u0016\u0010\f\u001a\u00020\r2\u0006\u0010\u000e\u001a\u00020\u000fH\u0086@\u00a2\u0006\u0002\u0010\u0010R\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0011"}, d2 = {"Lcom/bitcoding/bitbrains/data/repository/DeviceRepository;", "", "api", "Lcom/bitcoding/bitbrains/data/remote/ApiClient;", "store", "Lcom/bitcoding/bitbrains/data/local/SecureStore;", "(Lcom/bitcoding/bitbrains/data/remote/ApiClient;Lcom/bitcoding/bitbrains/data/local/SecureStore;)V", "listDevices", "Lcom/bitcoding/bitbrains/data/remote/dto/DevicesResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "register", "Lcom/bitcoding/bitbrains/data/remote/dto/RegisterDeviceResponse;", "unregister", "", "fcmToken", "", "(Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "app_debug"})
public final class DeviceRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.bitcoding.bitbrains.data.remote.ApiClient api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bitcoding.bitbrains.data.local.SecureStore store = null;
    
    public DeviceRepository(@org.jetbrains.annotations.NotNull()
    com.bitcoding.bitbrains.data.remote.ApiClient api, @org.jetbrains.annotations.NotNull()
    com.bitcoding.bitbrains.data.local.SecureStore store) {
        super();
    }
    
    /**
     * Register this install's FCM token against the user's email. Caches the
     * resulting [RegisterDeviceResponse.linked] flag so the UI can surface the
     * "finish Microsoft login" banner when it is false (§5, §6.4).
     *
     * Returns null on a transport failure (caller/Worker should retry). A 4xx
     * with a body is returned as-is — that is a real answer, not a retryable one.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object register(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bitcoding.bitbrains.data.remote.dto.RegisterDeviceResponse> $completion) {
        return null;
    }
    
    /**
     * §6.5 — deactivate this token so the backend stops pushing (logout).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object unregister(@org.jetbrains.annotations.NotNull()
    java.lang.String fcmToken, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    /**
     * §6.6 — this user's registered devices (diagnostics screen).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object listDevices(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bitcoding.bitbrains.data.remote.dto.DevicesResponse> $completion) {
        return null;
    }
}