package com.bitcoding.bitbrains.data.remote

import com.bitcoding.bitbrains.data.local.SecureStore
import okhttp3.Interceptor
import okhttp3.Response

/**
 * Adds "Authorization: Bearer <token>" to every request when a session token
 * exists (§5). Reads it fresh each call so a login/logout mid-session is picked
 * up without rebuilding the client.
 */
class AuthInterceptor(private val store: SecureStore) : Interceptor {
    override fun intercept(chain: Interceptor.Chain): Response {
        val token = store.sessionToken
        val request = if (token.isNullOrBlank()) {
            chain.request()
        } else {
            chain.request().newBuilder()
                .header("Authorization", "Bearer $token")
                .build()
        }
        return chain.proceed(request)
    }
}
