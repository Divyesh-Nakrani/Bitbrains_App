package com.bitcoding.bitbrains.data.local

import android.content.Context
import android.content.SharedPreferences
import androidx.security.crypto.EncryptedSharedPreferences
import androidx.security.crypto.MasterKey
import java.util.UUID

/**
 * Encrypted storage for everything sensitive: the backend session token, the
 * user's Microsoft email (our [employeeId]), and the current FCM token.
 *
 * MOBILE_APP_INTEGRATION.md §5: "Storage: EncryptedSharedPreferences. Never
 * plain SharedPreferences, never logs." The Microsoft access/refresh tokens
 * never leave the server — the app only ever holds the backend's own bearer.
 */
class SecureStore(context: Context) {

    private val prefs: SharedPreferences = run {
        val masterKey = MasterKey.Builder(context)
            .setKeyScheme(MasterKey.KeyScheme.AES256_GCM)
            .build()
        EncryptedSharedPreferences.create(
            context,
            "bitbrains_secure",
            masterKey,
            EncryptedSharedPreferences.PrefKeyEncryptionScheme.AES256_SIV,
            EncryptedSharedPreferences.PrefValueEncryptionScheme.AES256_GCM
        )
    }

    // --- Session token (backend JWT, 7-day life, no refresh) -----------------

    var sessionToken: String?
        get() = prefs.getString(KEY_TOKEN, null)
        set(value) = prefs.edit().putStringOrRemove(KEY_TOKEN, value).apply()

    val isLoggedIn: Boolean get() = !sessionToken.isNullOrBlank()

    // --- Identity ------------------------------------------------------------

    /** The user's Microsoft email — sent as employeeId on every call. */
    var email: String?
        get() = prefs.getString(KEY_EMAIL, null)
        set(value) = prefs.edit().putStringOrRemove(KEY_EMAIL, value).apply()

    /** Whether the last registration linked this device to a real user. */
    var linked: Boolean
        get() = prefs.getBoolean(KEY_LINKED, false)
        set(value) = prefs.edit().putBoolean(KEY_LINKED, value).apply()

    // --- FCM token -----------------------------------------------------------

    var fcmToken: String?
        get() = prefs.getString(KEY_FCM, null)
        set(value) = prefs.edit().putStringOrRemove(KEY_FCM, value).apply()

    // --- Stable install id (support only, never PII) -------------------------

    val deviceId: String
        get() = prefs.getString(KEY_DEVICE_ID, null) ?: UUID.randomUUID().toString()
            .also { prefs.edit().putString(KEY_DEVICE_ID, it).apply() }

    // --- Diagnostics ---------------------------------------------------------

    var lastPushAt: Long
        get() = prefs.getLong(KEY_LAST_PUSH, 0L)
        set(value) = prefs.edit().putLong(KEY_LAST_PUSH, value).apply()

    /** Wipe everything on logout (§8.7). */
    fun clearSession() {
        prefs.edit()
            .remove(KEY_TOKEN)
            .remove(KEY_EMAIL)
            .remove(KEY_LINKED)
            .apply()
    }

    private fun SharedPreferences.Editor.putStringOrRemove(
        key: String,
        value: String?
    ): SharedPreferences.Editor =
        if (value == null) remove(key) else putString(key, value)

    companion object {
        private const val KEY_TOKEN = "session_token"
        private const val KEY_EMAIL = "email"
        private const val KEY_LINKED = "linked"
        private const val KEY_FCM = "fcm_token"
        private const val KEY_DEVICE_ID = "device_id"
        private const val KEY_LAST_PUSH = "last_push_at"
    }
}
