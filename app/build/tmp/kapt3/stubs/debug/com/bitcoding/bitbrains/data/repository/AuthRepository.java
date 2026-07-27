package com.bitcoding.bitbrains.data.repository;

/**
 * Auth-related calls: /health, /auth/status, /profile, /logout (§6.1, 6.2, 6.8).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000<\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0002\b\u0002\u0018\u0000 \u00132\u00020\u0001:\u0001\u0013B\u0015\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u00a2\u0006\u0002\u0010\u0006J\u000e\u0010\u0007\u001a\u00020\bH\u0086@\u00a2\u0006\u0002\u0010\tJ\u001c\u0010\n\u001a\b\u0012\u0004\u0012\u00020\f0\u000bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\r\u0010\tJ\u001c\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\u000f0\u000bH\u0086@\u00f8\u0001\u0000\u00f8\u0001\u0001\u00a2\u0006\u0004\b\u0010\u0010\tJ\u0010\u0010\u0011\u001a\u0004\u0018\u00010\u0012H\u0086@\u00a2\u0006\u0002\u0010\tR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u0082\u0002\u000b\n\u0002\b!\n\u0005\b\u00a1\u001e0\u0001\u00a8\u0006\u0014"}, d2 = {"Lcom/bitcoding/bitbrains/data/repository/AuthRepository;", "", "api", "Lcom/bitcoding/bitbrains/data/remote/ApiClient;", "store", "Lcom/bitcoding/bitbrains/data/local/SecureStore;", "(Lcom/bitcoding/bitbrains/data/remote/ApiClient;Lcom/bitcoding/bitbrains/data/local/SecureStore;)V", "authStatus", "Lcom/bitcoding/bitbrains/data/remote/dto/AuthStatusResponse;", "(Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "health", "Lkotlin/Result;", "Lcom/bitcoding/bitbrains/data/remote/dto/HealthResponse;", "health-IoAF18A", "logout", "", "logout-IoAF18A", "refreshProfileEmail", "", "Companion", "app_debug"})
public final class AuthRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.bitcoding.bitbrains.data.remote.ApiClient api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bitcoding.bitbrains.data.local.SecureStore store = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AuthRepository";
    @org.jetbrains.annotations.NotNull()
    public static final com.bitcoding.bitbrains.data.repository.AuthRepository.Companion Companion = null;
    
    public AuthRepository(@org.jetbrains.annotations.NotNull()
    com.bitcoding.bitbrains.data.remote.ApiClient api, @org.jetbrains.annotations.NotNull()
    com.bitcoding.bitbrains.data.local.SecureStore store) {
        super();
    }
    
    /**
     * Decides login-screen vs dashboard on launch (§8.1). A 401 means the token
     * expired (7-day life, no refresh) — clear it so the app returns to login.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object authStatus(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bitcoding.bitbrains.data.remote.dto.AuthStatusResponse> $completion) {
        return null;
    }
    
    /**
     * Fetch profile and cache the email as our employeeId (§5, §8.2 step 5).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object refreshProfileEmail(@org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.String> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/bitcoding/bitbrains/data/repository/AuthRepository$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}