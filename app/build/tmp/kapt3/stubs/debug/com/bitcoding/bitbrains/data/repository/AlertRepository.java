package com.bitcoding.bitbrains.data.repository;

/**
 * Owns the received-alert history (Room) and the READ/IGNORED reporting call
 * (§6.7). The action call is the most important in the app, so its outcome is
 * modelled explicitly: [ActionOutcome] tells the Worker whether to retry.
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000V\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010\u000b\n\u0000\n\u0002\u0018\u0002\n\u0002\b\u0002\n\u0002\u0010 \n\u0000\n\u0002\u0010\b\n\u0002\b\u0002\n\u0002\u0010\u0002\n\u0000\n\u0002\u0010\u000e\n\u0002\b\u0003\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\t\n\u0002\b\u0004\u0018\u0000 \u001f2\u00020\u0001:\u0002\u001e\u001fB\u001d\u0012\u0006\u0010\u0002\u001a\u00020\u0003\u0012\u0006\u0010\u0004\u001a\u00020\u0005\u0012\u0006\u0010\u0006\u001a\u00020\u0007\u00a2\u0006\u0002\u0010\bJ\u0016\u0010\t\u001a\u00020\n2\u0006\u0010\u000b\u001a\u00020\fH\u0086@\u00a2\u0006\u0002\u0010\rJ\u001e\u0010\u000e\u001a\b\u0012\u0004\u0012\u00020\f0\u000f2\b\b\u0002\u0010\u0010\u001a\u00020\u0011H\u0086@\u00a2\u0006\u0002\u0010\u0012J\u001e\u0010\u0013\u001a\u00020\u00142\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u0016H\u0086@\u00a2\u0006\u0002\u0010\u0018J&\u0010\u0019\u001a\u00020\u001a2\u0006\u0010\u0015\u001a\u00020\u00162\u0006\u0010\u0017\u001a\u00020\u00162\u0006\u0010\u001b\u001a\u00020\u001cH\u0086@\u00a2\u0006\u0002\u0010\u001dR\u000e\u0010\u0002\u001a\u00020\u0003X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0004\u001a\u00020\u0005X\u0082\u0004\u00a2\u0006\u0002\n\u0000R\u000e\u0010\u0006\u001a\u00020\u0007X\u0082\u0004\u00a2\u0006\u0002\n\u0000\u00a8\u0006 "}, d2 = {"Lcom/bitcoding/bitbrains/data/repository/AlertRepository;", "", "api", "Lcom/bitcoding/bitbrains/data/remote/ApiClient;", "dao", "Lcom/bitcoding/bitbrains/data/local/AlertDao;", "store", "Lcom/bitcoding/bitbrains/data/local/SecureStore;", "(Lcom/bitcoding/bitbrains/data/remote/ApiClient;Lcom/bitcoding/bitbrains/data/local/AlertDao;Lcom/bitcoding/bitbrains/data/local/SecureStore;)V", "persist", "", "alert", "Lcom/bitcoding/bitbrains/data/local/AlertEntity;", "(Lcom/bitcoding/bitbrains/data/local/AlertEntity;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recent", "", "limit", "", "(ILkotlin/coroutines/Continuation;)Ljava/lang/Object;", "recordChoiceLocally", "", "messageId", "", "action", "(Ljava/lang/String;Ljava/lang/String;Lkotlin/coroutines/Continuation;)Ljava/lang/Object;", "reportAction", "Lcom/bitcoding/bitbrains/data/repository/AlertRepository$ActionOutcome;", "actionTimestamp", "", "(Ljava/lang/String;Ljava/lang/String;JLkotlin/coroutines/Continuation;)Ljava/lang/Object;", "ActionOutcome", "Companion", "app_debug"})
public final class AlertRepository {
    @org.jetbrains.annotations.NotNull()
    private final com.bitcoding.bitbrains.data.remote.ApiClient api = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bitcoding.bitbrains.data.local.AlertDao dao = null;
    @org.jetbrains.annotations.NotNull()
    private final com.bitcoding.bitbrains.data.local.SecureStore store = null;
    @org.jetbrains.annotations.NotNull()
    private static final java.lang.String TAG = "AlertRepository";
    @org.jetbrains.annotations.NotNull()
    public static final com.bitcoding.bitbrains.data.repository.AlertRepository.Companion Companion = null;
    
    public AlertRepository(@org.jetbrains.annotations.NotNull()
    com.bitcoding.bitbrains.data.remote.ApiClient api, @org.jetbrains.annotations.NotNull()
    com.bitcoding.bitbrains.data.local.AlertDao dao, @org.jetbrains.annotations.NotNull()
    com.bitcoding.bitbrains.data.local.SecureStore store) {
        super();
    }
    
    /**
     * true if this messageId was newly inserted; false if it was a duplicate push.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object persist(@org.jetbrains.annotations.NotNull()
    com.bitcoding.bitbrains.data.local.AlertEntity alert, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.lang.Boolean> $completion) {
        return null;
    }
    
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object recent(int limit, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super java.util.List<com.bitcoding.bitbrains.data.local.AlertEntity>> $completion) {
        return null;
    }
    
    /**
     * Record the user's choice locally before the network call (survives death).
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object recordChoiceLocally(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    java.lang.String action, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super kotlin.Unit> $completion) {
        return null;
    }
    
    /**
     * Report READ/IGNORED to the backend (§6.7). Semantics that matter:
     * - 200 with resolved=false is still SUCCESS (e.g. a dev.test push). Never retry it.
     * - 400/404/422 are terminal request problems — do not retry.
     * - 5xx / timeout / offline are retryable.
     */
    @org.jetbrains.annotations.Nullable()
    public final java.lang.Object reportAction(@org.jetbrains.annotations.NotNull()
    java.lang.String messageId, @org.jetbrains.annotations.NotNull()
    java.lang.String action, long actionTimestamp, @org.jetbrains.annotations.NotNull()
    kotlin.coroutines.Continuation<? super com.bitcoding.bitbrains.data.repository.AlertRepository.ActionOutcome> $completion) {
        return null;
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\f\n\u0002\u0018\u0002\n\u0002\u0010\u0010\n\u0002\b\u0005\b\u0086\u0081\u0002\u0018\u00002\b\u0012\u0004\u0012\u00020\u00000\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002j\u0002\b\u0003j\u0002\b\u0004j\u0002\b\u0005\u00a8\u0006\u0006"}, d2 = {"Lcom/bitcoding/bitbrains/data/repository/AlertRepository$ActionOutcome;", "", "(Ljava/lang/String;I)V", "Success", "Terminal", "Retry", "app_debug"})
    public static enum ActionOutcome {
        /*public static final*/ Success /* = new Success() */,
        /*public static final*/ Terminal /* = new Terminal() */,
        /*public static final*/ Retry /* = new Retry() */;
        
        ActionOutcome() {
        }
        
        @org.jetbrains.annotations.NotNull()
        public static kotlin.enums.EnumEntries<com.bitcoding.bitbrains.data.repository.AlertRepository.ActionOutcome> getEntries() {
            return null;
        }
    }
    
    @kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0012\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0010\u000e\n\u0000\b\u0086\u0003\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002R\u000e\u0010\u0003\u001a\u00020\u0004X\u0082T\u00a2\u0006\u0002\n\u0000\u00a8\u0006\u0005"}, d2 = {"Lcom/bitcoding/bitbrains/data/repository/AlertRepository$Companion;", "", "()V", "TAG", "", "app_debug"})
    public static final class Companion {
        
        private Companion() {
            super();
        }
    }
}