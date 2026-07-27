package com.bitcoding.bitbrains.data.remote.dto

import com.google.gson.annotations.SerializedName

/*
 * ---------------------------------------------------------------------------
 * snake_case DTOs — the older /messages and /profile endpoints (§6.8).
 * Kept in their own file/package deliberately (guide §13.4): these routes are
 * snake_case while everything under /api is camelCase. Do not mix them.
 * ---------------------------------------------------------------------------
 */

// GET /profile (§6.8) — source of the employeeId (its email).
data class ProfileResponse(
    @SerializedName("graph_user_id") val graphUserId: String? = null,
    @SerializedName("display_name") val displayName: String? = null,
    val email: String? = null,
    @SerializedName("tenant_id") val tenantId: String? = null
)

// GET /messages?ack_status=escalated (§6.8)
data class MessagesResponse(
    val count: Int = 0,
    @SerializedName("counts_by_status") val countsByStatus: Map<String, Int> = emptyMap(),
    val messages: List<MessageDto> = emptyList()
)

data class MessageDto(
    val id: String? = null,
    @SerializedName("graph_message_id") val graphMessageId: String? = null,
    @SerializedName("graph_chat_id") val graphChatId: String? = null,
    @SerializedName("chat_type") val chatType: String? = null,
    val sender: SenderDto? = null,
    @SerializedName("is_from_me") val isFromMe: Boolean = false,
    val body: String? = null,
    val importance: String? = null,
    @SerializedName("sent_at") val sentAt: String? = null,
    @SerializedName("received_at") val receivedAt: String? = null,
    val acknowledgement: AcknowledgementDto? = null
)

data class SenderDto(
    @SerializedName("graph_user_id") val graphUserId: String? = null,
    @SerializedName("display_name") val displayName: String? = null,
    val email: String? = null
)

data class AcknowledgementDto(
    val status: String? = null,
    @SerializedName("cooldown_seconds") val cooldownSeconds: Int? = null,
    @SerializedName("due_at") val dueAt: String? = null,
    @SerializedName("acknowledged_at") val acknowledgedAt: String? = null,
    @SerializedName("ack_source") val ackSource: String? = null,
    @SerializedName("ack_detail") val ackDetail: String? = null,
    @SerializedName("escalated_at") val escalatedAt: String? = null,
    val checks: Int = 0,
    @SerializedName("last_error") val lastError: String? = null
)
