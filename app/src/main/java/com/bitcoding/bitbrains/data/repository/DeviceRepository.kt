package com.bitcoding.bitbrains.data.repository

import android.os.Build
import com.bitcoding.bitbrains.BuildConfig
import com.bitcoding.bitbrains.data.local.SecureStore
import com.bitcoding.bitbrains.data.remote.ApiClient
import com.bitcoding.bitbrains.data.remote.dto.DevicesResponse
import com.bitcoding.bitbrains.data.remote.dto.RegisterDeviceRequest
import com.bitcoding.bitbrains.data.remote.dto.RegisterDeviceResponse
import com.bitcoding.bitbrains.data.remote.dto.UnregisterDeviceRequest

/**
 * Device registration / unregistration / listing (§6.4-6.6).
 */
class DeviceRepository(
    private val api: ApiClient,
    private val store: SecureStore
) {

    /**
     * Register this install's FCM token against the user's email. Caches the
     * resulting [RegisterDeviceResponse.linked] flag so the UI can surface the
     * "finish Microsoft login" banner when it is false (§5, §6.4).
     *
     * Returns null on a transport failure (caller/Worker should retry). A 4xx
     * with a body is returned as-is — that is a real answer, not a retryable one.
     */
    suspend fun register(): RegisterDeviceResponse? {
        val email = store.email
        val fcmToken = store.fcmToken
        if (email.isNullOrBlank() || fcmToken.isNullOrBlank()) {
            // Nothing to register yet — happens when onNewToken fires pre-login.
            return null
        }
        val body = RegisterDeviceRequest(
            employeeId = email,
            fcmToken = fcmToken,
            platform = "android",
            deviceId = store.deviceId,
            deviceModel = "${Build.MANUFACTURER} ${Build.MODEL}",
            appVersion = BuildConfig.VERSION_NAME
        )
        val resp = api.service.registerDevice(body)
        val result = resp.body()
        if (result != null) {
            store.linked = result.linked
        }
        return result
    }

    /** §6.5 — deactivate this token so the backend stops pushing (logout). */
    suspend fun unregister(fcmToken: String): Boolean = runCatching {
        api.service.unregisterDevice(UnregisterDeviceRequest(fcmToken)).body()?.success == true
    }.getOrDefault(false)

    /** §6.6 — this user's registered devices (diagnostics screen). */
    suspend fun listDevices(): DevicesResponse? = runCatching {
        api.service.listDevices().body()
    }.getOrNull()
}
