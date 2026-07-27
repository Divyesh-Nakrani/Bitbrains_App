package com.bitcoding.bitbrains.ui.home

import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.view.View
import android.widget.Button
import android.widget.TextView
import android.widget.Toast
import androidx.activity.result.contract.ActivityResultContracts
import androidx.appcompat.app.AppCompatActivity
import androidx.core.content.ContextCompat
import androidx.lifecycle.lifecycleScope
import com.bitcoding.bitbrains.R
import com.bitcoding.bitbrains.databinding.ActivityHomeBinding
import com.bitcoding.bitbrains.di.ServiceLocator
import com.bitcoding.bitbrains.ui.login.LoginActivity
import com.bitcoding.bitbrains.ui.settings.DiagnosticsActivity
import com.bitcoding.bitbrains.util.PermissionHelper
import com.bitcoding.bitbrains.work.RegisterDeviceWorker
import com.google.firebase.messaging.FirebaseMessaging
import kotlinx.coroutines.launch

/**
 * The dashboard (§8.3). Reached only when a session exists, so it doubles as the
 * "authenticated with Teams" confirmation screen. On every resume it re-validates
 * the session, refreshes the FCM token + registration (idempotent), surfaces the
 * linked state, loads the escalated-message list, and reflects the live
 * alert-permission state (managed inline here rather than back on the login screen).
 */
class HomeActivity : AppCompatActivity() {

    private lateinit var binding: ActivityHomeBinding
    private val store get() = ServiceLocator.secureStore

    private val requestNotifications =
        registerForActivityResult(ActivityResultContracts.RequestPermission()) { updatePermissions() }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityHomeBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonRefresh.setOnClickListener { refresh() }
        binding.buttonDiagnostics.setOnClickListener {
            startActivity(Intent(this, DiagnosticsActivity::class.java))
        }
        binding.buttonLogout.setOnClickListener { logout() }

        // Inline alert-permission management.
        binding.btnAllowNotifications.setOnClickListener { requestNotifications() }
        binding.btnAllowFullscreen.setOnClickListener { openFullScreenSettings() }
        binding.btnAllowBattery.setOnClickListener { openBatterySettings() }
        binding.btnAllowOverlay.setOnClickListener { openOverlaySettings() }
    }

    override fun onResume() {
        super.onResume()
        refresh()
        updatePermissions()
    }

    private fun refresh() {
        lifecycleScope.launch {
            // 1. Validate the session; a 401 clears the token → back to login.
            val status = ServiceLocator.authRepository.authStatus()
            if (!status.authenticated) {
                goLogin()
                return@launch
            }
            binding.textDisplayName.text = status.displayName ?: "Signed in"

            // 2. Cache the email (employeeId) from /profile.
            ServiceLocator.authRepository.refreshProfileEmail()
            binding.textEmail.text = store.email ?: status.email ?: ""

            // 3. Ensure we have the current FCM token, then register (idempotent).
            ensureFcmTokenThenRegister()

            // 4. Load escalated messages.
            loadEscalated()

            updateStatus()
        }
    }

    private fun ensureFcmTokenThenRegister() {
        FirebaseMessaging.getInstance().token.addOnCompleteListener { task ->
            if (task.isSuccessful) {
                store.fcmToken = task.result
            }
            // Register directly for an immediate linked result, and enqueue the
            // Worker as the retry fallback (§8.3, §8.4).
            lifecycleScope.launch {
                val resp = ServiceLocator.deviceRepository.register()
                if (resp != null) {
                    binding.bannerUnlinked.visibility =
                        if (resp.linked) View.GONE else View.VISIBLE
                    if (!resp.linked && resp.detail != null) {
                        binding.bannerUnlinked.text = resp.detail
                    }
                }
                RegisterDeviceWorker.enqueue(applicationContext)
                updateStatus()
            }
        }
    }

    private suspend fun loadEscalated() {
        val resp = runCatching { ServiceLocator.apiClient.service.listMessages() }.getOrNull()
        val body = resp?.body()
        binding.textEscalated.text = when {
            body == null -> "Couldn't load messages."
            body.messages.isEmpty() -> "No unanswered messages. You're all caught up."
            else -> body.messages.joinToString("\n\n") { m ->
                val sender = m.sender?.displayName ?: "Unknown"
                "• $sender\n  ${m.body ?: ""}"
            }
        }
    }

    private fun updateStatus() {
        binding.textStatus.text =
            if (store.linked) "✔ Device linked — critical alerts will reach you."
            else "◌ Device not linked yet. Keep this screen open a moment while it registers."
        val color = if (store.linked) R.color.success else R.color.muted
        binding.textStatus.setTextColor(ContextCompat.getColor(this, color))
    }

    // ---- Alert permissions (inline) -----------------------------------------

    private fun updatePermissions() {
        bindPerm(
            PermissionHelper.hasNotificationPermission(this),
            binding.textNotifStatus, binding.btnAllowNotifications
        )
        bindPerm(
            PermissionHelper.canUseFullScreenIntent(this),
            binding.textFullscreenStatus, binding.btnAllowFullscreen
        )
        bindPerm(
            PermissionHelper.isIgnoringBatteryOptimizations(this),
            binding.textBatteryStatus, binding.btnAllowBattery
        )
        bindPerm(
            PermissionHelper.canDrawOverlays(this),
            binding.textOverlayStatus, binding.btnAllowOverlay
        )
    }

    private fun bindPerm(granted: Boolean, status: TextView, button: Button) {
        if (granted) {
            status.text = "✔ Allowed"
            status.setTextColor(ContextCompat.getColor(this, R.color.success))
            button.text = "Allowed"
            button.isEnabled = false
        } else {
            status.text = "Not allowed"
            status.setTextColor(ContextCompat.getColor(this, R.color.muted))
            button.text = "Allow"
            button.isEnabled = true
        }
    }

    private fun requestNotifications() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.TIRAMISU &&
            !PermissionHelper.hasNotificationPermission(this)
        ) {
            requestNotifications.launch(android.Manifest.permission.POST_NOTIFICATIONS)
        } else {
            toast("Notifications already allowed.")
        }
    }

    private fun openFullScreenSettings() {
        val intent = PermissionHelper.fullScreenIntentSettings(this)
        if (intent == null || PermissionHelper.canUseFullScreenIntent(this)) {
            toast("Full-screen alerts already allowed.")
        } else {
            startActivity(intent)
        }
    }

    private fun openBatterySettings() {
        if (PermissionHelper.isIgnoringBatteryOptimizations(this)) {
            toast("Battery optimization already disabled.")
        } else {
            startActivity(PermissionHelper.batteryOptimizationSettings(this))
        }
    }

    private fun openOverlaySettings() {
        if (PermissionHelper.canDrawOverlays(this)) {
            toast("Display over other apps already allowed.")
        } else {
            startActivity(PermissionHelper.overlaySettings(this))
        }
    }

    // ---- Session ------------------------------------------------------------

    private fun logout() {
        lifecycleScope.launch {
            // §8.7 order: unregister the token, clear the server session, wipe locally.
            store.fcmToken?.let { ServiceLocator.deviceRepository.unregister(it) }
            ServiceLocator.authRepository.logout()
            store.clearSession()
            goLogin()
        }
    }

    private fun goLogin() {
        startActivity(
            Intent(this, LoginActivity::class.java)
                .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_NEW_TASK)
        )
        finish()
    }

    private fun toast(msg: String) = Toast.makeText(this, msg, Toast.LENGTH_SHORT).show()
}
