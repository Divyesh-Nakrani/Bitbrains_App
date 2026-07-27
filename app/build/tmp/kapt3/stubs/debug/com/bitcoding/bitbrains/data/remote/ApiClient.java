package com.bitcoding.bitbrains.data.remote;

/**
 * Builds the single Retrofit [ApiService]. Timeouts per §10 (connect 10s,
 * read 30s). Logging stays at BASIC and redacts the Authorization header — we
 * must never log the session token or the FCM token (§10).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000,\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0003\u0018\u00002\u00020\u0001B\r\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u00a2\u0006\u0002\u0010\u0004R\u000e\u0010\u0005\u001a\u00020\u0006X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0007\u001a\u00020\bX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\t\u001a\u00020\nX\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u0011\u0010\u000b\u001a\u00020\f\u00a2\u0006\b\n\u0000\u001a\u0004\b\r\u0010\u000e\u00a8\u0006\u000f"}, d2 = {"Lcom/bitcoding/bitbrains/data/remote/ApiClient;", "", "store", "Lcom/bitcoding/bitbrains/data/local/SecureStore;", "(Lcom/bitcoding/bitbrains/data/local/SecureStore;)V", "logging", "Lokhttp3/logging/HttpLoggingInterceptor;", "ngrokHeader", "Lokhttp3/Interceptor;", "okHttpClient", "Lokhttp3/OkHttpClient;", "service", "Lcom/bitcoding/bitbrains/data/remote/ApiService;", "getService", "()Lcom/bitcoding/bitbrains/data/remote/ApiService;", "app_debug"})
public final class ApiClient {
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.logging.HttpLoggingInterceptor logging = null;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.Interceptor ngrokHeader = null;
    @org.jetbrains.annotations.NotNull()
    private final okhttp3.OkHttpClient okHttpClient = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bitcoding.bitbrains.data.remote.ApiService service = null;
    
    public ApiClient(@org.jetbrains.annotations.NotNull()
    com.bitcoding.bitbrains.data.local.SecureStore store) {
        super();
    }
    
    @org.jetbrains.annotations.NotNull()
    public final com.bitcoding.bitbrains.data.remote.ApiService getService() {
        return null;
    }
}