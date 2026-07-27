package com.bitcoding.bitbrains.data.remote.dto

import com.google.gson.annotations.SerializedName

/*
 * ---------------------------------------------------------------------------
 * camelCase DTOs — the /api mobile endpoints (§6.1, 6.2, 6.4-6.7).
 * Keep these in a SEPARATE file from the snake_case /messages DTOs so the two
 * naming styles are never mixed (guide §13.4).
 * ---------------------------------------------------------------------------
 */

// GET /health (§6.1) — no auth
data class HealthResponse(
    val status: String? = null,
    @SerializedName("ack_cooldown_seconds") val ackCooldownSeconds: Int? = null,
    @SerializedName("ack_monitor_enabled") val ackMonitorEnabled: Boolean? = null,
    @SerializedName("webhook_ready") val webhookReady: Boolean? = null,
    @SerializedName("push_ready") val pushReady: Boolean? = null,
    @SerializedName("push_error") val pushError: String? = null
)

// GET /auth/status (§6.2) — bearer optional
data class AuthStatusResponse(
    val authenticated: Boolean = false,
    @SerializedName("display_name") val displayName: String? = null,
    val email: String? = null
)

// POST /api/devices/register (§6.4)
data class RegisterDeviceRequest(
    val employeeId: String,
    val fcmToken: String,
    val platform: String = "android",
    val deviceId: String? = null,
    val deviceModel: String? = null,
    val appVersion: String? = null
)

data class RegisterDeviceResponse(
    val success: Boolean = false,
    val deviceId: String? = null,
    /** MUST be true, else this device can never be pushed to (§5). */
    val linked: Boolean = false,
    val detail: String? = null
)

// POST /api/devices/unregister (§6.5) — no auth
data class UnregisterDeviceRequest(
    val fcmToken: String
)

data class UnregisterDeviceResponse(
    val success: Boolean = false,
    val removed: Boolean = false
)

// GET /api/devices (§6.6) — auth required
data class DevicesResponse(
    val count: Int = 0,
    val devices: List<DeviceInfo> = emptyList()
)

data class DeviceInfo(
    val id: String? = null,
    val employeeId: String? = null,
    val userId: String? = null,
    val platform: String? = null,
    val deviceId: String? = null,
    val deviceModel: String? = null,
    val appVersion: String? = null,
    val fcmTokenPreview: String? = null,
    val isActive: Boolean = false,
    val registeredAt: String? = null,
    val lastSeenAt: String? = null,
    val lastPushAt: String? = null,
    val failureCount: Int = 0,
    val lastError: String? = null
)

// POST /api/messages/{messageId}/action (§6.7) — camelCase request...
data class MessageActionRequest(
    val action: String,                 // "READ" | "IGNORED"
    val employeeId: String? = null,
    val actionTimestamp: Long? = null,  // epoch millis, device clock
    val fcmToken: String? = null
)

// ...but a snake_case response body.
data class MessageActionResponse(
    val success: Boolean = false,
    @SerializedName("action_id") val actionId: String? = null,
    @SerializedName("message_id") val messageId: String? = null,
    val resolved: Boolean = false,
    val acknowledged: Boolean = false
)
