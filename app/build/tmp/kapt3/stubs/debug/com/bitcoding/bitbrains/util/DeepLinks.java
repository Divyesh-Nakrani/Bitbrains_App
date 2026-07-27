package com.bitcoding.bitbrains.util;

/**
 * Deep-link builders. "Open in Teams" jumps straight to the chat that raised
 * the alert (§9, §8.5 action row).
 */
@kotlin.Metadata(mv = {1, 9, 0}, k = 1, xi = 48, d1 = {"\u0000\u0018\n\u0002\u0018\u0002\n\u0002\u0010\u0000\n\u0002\b\u0002\n\u0002\u0018\u0002\n\u0000\n\u0002\u0010\u000e\n\u0000\b\u00c6\u0002\u0018\u00002\u00020\u0001B\u0007\b\u0002\u00a2\u0006\u0002\u0010\u0002J\u0012\u0010\u0003\u001a\u0004\u0018\u00010\u00042\b\u0010\u0005\u001a\u0004\u0018\u00010\u0006\u00a8\u0006\u0007"}, d2 = {"Lcom/bitcoding/bitbrains/util/DeepLinks;", "", "()V", "teamsChat", "Landroid/net/Uri;", "chatId", "", "app_debug"})
public final class DeepLinks {
    @org.jetbrains.annotations.NotNull()
    public static final com.bitcoding.bitbrains.util.DeepLinks INSTANCE = null;
    
    private DeepLinks() {
        super();
    }
    
    /**
     * msteams://l/chat/{chatId} — opens the Teams app on the relevant chat.
     */
    @org.jetbrains.annotations.Nullable()
    public final android.net.Uri teamsChat(@org.jetbrains.annotations.Nullable()
    java.lang.String chatId) {
        return null;
    }
}