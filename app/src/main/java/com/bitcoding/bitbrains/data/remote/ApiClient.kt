package com.bitcoding.bitbrains.data.remote

import com.bitcoding.bitbrains.BuildConfig
import com.bitcoding.bitbrains.data.local.SecureStore
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import java.util.concurrent.TimeUnit

/**
 * Builds the single Retrofit [ApiService]. Timeouts per §10 (connect 10s,
 * read 30s). Logging stays at BASIC and redacts the Authorization header — we
 * must never log the session token or the FCM token (§10).
 */
class ApiClient(store: SecureStore) {

    private val logging = HttpLoggingInterceptor().apply {
        // BASIC = method/url/status only. BODY would leak fcmToken/session token.
        level = if (BuildConfig.DEBUG) HttpLoggingInterceptor.Level.BASIC
        else HttpLoggingInterceptor.Level.NONE
        redactHeader("Authorization")
    }

    // ngrok's free tier serves an HTML interstitial to non-browser clients on
    // some requests; this header opts out so we always get the real JSON body.
    private val ngrokHeader = Interceptor { chain ->
        chain.proceed(
            chain.request().newBuilder()
                .header("ngrok-skip-browser-warning", "true")
                .build()
        )
    }

    private val okHttpClient = OkHttpClient.Builder()
        .addInterceptor(AuthInterceptor(store))
        .addInterceptor(ngrokHeader)
        .addInterceptor(logging)
        .connectTimeout(10, TimeUnit.SECONDS)
        .readTimeout(30, TimeUnit.SECONDS)
        .build()

    val service: ApiService = Retrofit.Builder()
        .baseUrl(BuildConfig.API_BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(GsonConverterFactory.create())
        .build()
        .create(ApiService::class.java)
}
