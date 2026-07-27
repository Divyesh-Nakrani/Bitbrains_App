package com.bitcoding.bitbrains.data.local

import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * A single escalated-message push, persisted the moment it arrives (§8.5 step 2:
 * "the process can die before the user reacts"). [messageId] is the primary key,
 * which also gives us free de-duplication — FCM can deliver the same push twice.
 */
@Entity(tableName = "alerts")
data class AlertEntity(
    @PrimaryKey val messageId: String,
    val event: String,
    val senderName: String,
    val priority: String,
    val messageText: String,
    val chatId: String?,
    val chatType: String?,
    val cooldownSeconds: String?,
    val sentAt: String?,
    val receivedAt: String?,
    val escalatedAt: String?,
    /** When this device received the push (device clock, epoch millis). */
    val receivedAtLocal: Long,
    /** null until the user reacts; then "READ" or "IGNORED". */
    val action: String? = null,
    /** true once the backend has acknowledged the action call with a 200. */
    val actionSynced: Boolean = false
)
