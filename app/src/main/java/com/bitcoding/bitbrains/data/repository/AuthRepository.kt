package com.bitcoding.bitbrains.data.repository

import android.util.Log
import com.bitcoding.bitbrains.data.local.SecureStore
import com.bitcoding.bitbrains.data.remote.ApiClient
import com.bitcoding.bitbrains.data.remote.dto.AuthStatusResponse
import com.bitcoding.bitbrains.data.remote.dto.HealthResponse

/**
 * Auth-related calls: /health, /auth/status, /profile, /logout (§6.1, 6.2, 6.8).
 */
class AuthRepository(
    private val api: ApiClient,
    private val store: SecureStore
) {

    suspend fun health(): Result<HealthResponse> = runCatching {
        val resp = api.service.health()
        resp.body() ?: error("health ${resp.code()}")
    }

    /**
     * Decides login-screen vs dashboard on launch (§8.1). A 401 means the token
     * expired (7-day life, no refresh) — clear it so the app returns to login.
     */
    suspend fun authStatus(): AuthStatusResponse = runCatching {
        val resp = api.service.authStatus()
        if (resp.code() == 401) {
            store.clearSession()
            return AuthStatusResponse(authenticated = false)
        }
        resp.body() ?: AuthStatusResponse(authenticated = false)
    }.getOrElse {
        Log.w(TAG, "authStatus failed: ${it.message}")
        AuthStatusResponse(authenticated = false)
    }

    /** Fetch profile and cache the email as our employeeId (§5, §8.2 step 5). */
    suspend fun refreshProfileEmail(): String? = runCatching {
        val resp = api.service.profile()
        val email = resp.body()?.email
        if (!email.isNullOrBlank()) store.email = email
        email
    }.getOrNull()

    /** §8.7 — clear the server session. Local wipe is the caller's job. */
    suspend fun logout(): Result<Unit> = runCatching {
        api.service.logout()
        Unit
    }

    companion object {
        private const val TAG = "AuthRepository"
    }
}
