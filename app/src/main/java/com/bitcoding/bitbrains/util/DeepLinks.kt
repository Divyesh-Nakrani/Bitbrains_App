package com.bitcoding.bitbrains.util

import android.net.Uri

/**
 * Deep-link builders. "Open in Teams" jumps straight to the chat that raised
 * the alert (§9, §8.5 action row).
 */
object DeepLinks {

    /** msteams://l/chat/{chatId} — opens the Teams app on the relevant chat. */
    fun teamsChat(chatId: String?): Uri? {
        if (chatId.isNullOrBlank()) return null
        return Uri.parse("msteams://l/chat/${Uri.encode(chatId)}")
    }
}
