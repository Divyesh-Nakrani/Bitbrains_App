package com.bitcoding.bitbrains.data.remote

import com.bitcoding.bitbrains.data.remote.dto.AuthStatusResponse
import com.bitcoding.bitbrains.data.remote.dto.DevicesResponse
import com.bitcoding.bitbrains.data.remote.dto.HealthResponse
import com.bitcoding.bitbrains.data.remote.dto.MessageActionRequest
import com.bitcoding.bitbrains.data.remote.dto.MessageActionResponse
import com.bitcoding.bitbrains.data.remote.dto.MessagesResponse
import com.bitcoding.bitbrains.data.remote.dto.ProfileResponse
import com.bitcoding.bitbrains.data.remote.dto.RegisterDeviceRequest
import com.bitcoding.bitbrains.data.remote.dto.RegisterDeviceResponse
import com.bitcoding.bitbrains.data.remote.dto.UnregisterDeviceRequest
import com.bitcoding.bitbrains.data.remote.dto.UnregisterDeviceResponse
import retrofit2.Response
import retrofit2.http.Body
import retrofit2.http.GET
import retrofit2.http.POST
import retrofit2.http.Path
import retrofit2.http.Query

/**
 * Every endpoint the app touches (MOBILE_APP_INTEGRATION.md §6).
 * NOTE: /login is intentionally absent — it is a browser 302 and must only be
 * opened in a Chrome Custom Tab, never via Retrofit (§6.3).
 */
interface ApiService {

    // §6.1 — no auth
    @GET("health")
    suspend fun health(): Response<HealthResponse>

    // §6.2 — bearer optional (AuthInterceptor adds it when present)
    @GET("auth/status")
    suspend fun authStatus(): Response<AuthStatusResponse>

    // §6.4 — auth optional (send it anyway)
    @POST("api/devices/register")
    suspend fun registerDevice(@Body body: RegisterDeviceRequest): Response<RegisterDeviceResponse>

    // §6.5 — no auth
    @POST("api/devices/unregister")
    suspend fun unregisterDevice(@Body body: UnregisterDeviceRequest): Response<UnregisterDeviceResponse>

    // §6.6 — auth required
    @GET("api/devices")
    suspend fun listDevices(): Response<DevicesResponse>

    // §6.7 — auth optional; the single most important call in the app
    @POST("api/messages/{messageId}/action")
    suspend fun postMessageAction(
        @Path("messageId") messageId: String,
        @Body body: MessageActionRequest
    ): Response<MessageActionResponse>

    // §6.8 — auth required (snake_case route)
    @GET("messages")
    suspend fun listMessages(
        @Query("ack_status") ackStatus: String = "escalated",
        @Query("limit") limit: Int = 50,
        @Query("offset") offset: Int = 0
    ): Response<MessagesResponse>

    // §6.8 — auth required
    @GET("profile")
    suspend fun profile(): Response<ProfileResponse>

    // §6.8 — auth required
    @POST("logout")
    suspend fun logout(): Response<Unit>
}
