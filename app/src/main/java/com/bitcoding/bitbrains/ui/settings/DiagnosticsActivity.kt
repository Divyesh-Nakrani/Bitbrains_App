package com.bitcoding.bitbrains.ui.settings

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.lifecycleScope
import com.bitcoding.bitbrains.databinding.ActivityDiagnosticsBinding
import com.bitcoding.bitbrains.di.ServiceLocator
import kotlinx.coroutines.launch
import java.text.SimpleDateFormat
import java.util.Date
import java.util.Locale

/**
 * Debug/settings screen (§6.1, §6.6): backend health, this user's registered
 * devices, and the last push received. Surfaces push_ready=false — "it saves
 * hours" (§6.1).
 */
class DiagnosticsActivity : AppCompatActivity() {

    private lateinit var binding: ActivityDiagnosticsBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityDiagnosticsBinding.inflate(layoutInflater)
        setContentView(binding.root)
        binding.buttonRefresh.setOnClickListener { load() }
    }

    override fun onResume() {
        super.onResume()
        load()
    }

    private fun load() {
        lifecycleScope.launch {
            // Health (no auth)
            binding.textHealth.text = ServiceLocator.authRepository.health().fold(
                onSuccess = { h ->
                    buildString {
                        append("status: ${h.status}\n")
                        append("push_ready: ${h.pushReady}\n")
                        if (h.pushReady == false) append("push_error: ${h.pushError}\n")
                        append("webhook_ready: ${h.webhookReady}\n")
                        append("ack_cooldown_seconds: ${h.ackCooldownSeconds}\n")
                        append("ack_monitor_enabled: ${h.ackMonitorEnabled}")
                    }
                },
                onFailure = { "Unreachable: ${it.message}" }
            )

            // Devices (auth required)
            val devices = ServiceLocator.deviceRepository.listDevices()
            binding.textDevices.text = when {
                devices == null -> "Couldn't load (need a valid session)."
                devices.devices.isEmpty() -> "No devices registered."
                else -> devices.devices.joinToString("\n\n") { d ->
                    "id: ${d.id}\n" +
                        "active: ${d.isActive}\n" +
                        "model: ${d.deviceModel}\n" +
                        "token: ${d.fcmTokenPreview}\n" +
                        "lastPushAt: ${d.lastPushAt}\n" +
                        "failures: ${d.failureCount}  err: ${d.lastError}"
                }
            }

            // Last push (local)
            val last = ServiceLocator.secureStore.lastPushAt
            binding.textLastPush.text = if (last == 0L) "—"
            else SimpleDateFormat("yyyy-MM-dd HH:mm:ss", Locale.getDefault()).format(Date(last))
        }
    }
}
