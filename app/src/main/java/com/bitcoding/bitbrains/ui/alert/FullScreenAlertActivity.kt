package com.bitcoding.bitbrains.ui.alert

import android.app.KeyguardManager
import android.content.Context
import android.os.Build
import android.os.Bundle
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import com.bitcoding.bitbrains.databinding.ActivityFullScreenAlertBinding
import com.bitcoding.bitbrains.push.AlertNotifier
import com.bitcoding.bitbrains.util.AlertSoundPlayer
import com.bitcoding.bitbrains.work.ReportActionWorker

/**
 * The Critical alarm screen (§7). Appears over the lockscreen, wakes the display,
 * loops an alarm sound + vibration until the user chooses. Read/Ignore are
 * reported through [ReportActionWorker] so the call survives being offline (§8.6).
 */
class FullScreenAlertActivity : AppCompatActivity() {

    private lateinit var binding: ActivityFullScreenAlertBinding
    private lateinit var soundPlayer: AlertSoundPlayer

    private var messageId: String = ""
    private var resolved = false

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        showOverLockScreen()

        binding = ActivityFullScreenAlertBinding.inflate(layoutInflater)
        setContentView(binding.root)

        messageId = intent.getStringExtra(EXTRA_MESSAGE_ID) ?: ""
        binding.textPriority.text = (intent.getStringExtra(EXTRA_PRIORITY) ?: "Critical").uppercase()
        binding.textSender.text = intent.getStringExtra(EXTRA_SENDER) ?: "Unknown sender"
        binding.textMessage.text = intent.getStringExtra(EXTRA_TEXT) ?: ""

        soundPlayer = AlertSoundPlayer(applicationContext)
        soundPlayer.start()

        binding.buttonMarkRead.setOnClickListener { resolve("READ") }
        binding.buttonIgnore.setOnClickListener { resolve("IGNORED") }
    }

    private fun showOverLockScreen() {
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O_MR1) {
            setShowWhenLocked(true)
            setTurnScreenOn(true)
            (getSystemService(Context.KEYGUARD_SERVICE) as KeyguardManager)
                .requestDismissKeyguard(this, null)
        } else {
            @Suppress("DEPRECATION")
            window.addFlags(
                WindowManager.LayoutParams.FLAG_SHOW_WHEN_LOCKED or
                    WindowManager.LayoutParams.FLAG_TURN_SCREEN_ON or
                    WindowManager.LayoutParams.FLAG_DISMISS_KEYGUARD or
                    WindowManager.LayoutParams.FLAG_KEEP_SCREEN_ON
            )
        }
    }

    private fun resolve(action: String) {
        if (resolved) return
        resolved = true
        soundPlayer.stop()
        binding.buttonMarkRead.isEnabled = false
        binding.buttonIgnore.isEnabled = false

        if (messageId.isNotEmpty()) {
            ReportActionWorker.enqueue(applicationContext, messageId, action)
            AlertNotifier.cancel(applicationContext, messageId)
        }
        finish()
    }

    override fun onDestroy() {
        soundPlayer.stop()
        super.onDestroy()
    }

    // Leaving via Home without choosing counts as IGNORED — "silence is data" (§8.6).
    override fun onUserLeaveHint() {
        if (!resolved) resolve("IGNORED")
        super.onUserLeaveHint()
    }

    // Back must not silently dismiss an unacknowledged Critical alarm.
    @Deprecated("Intentional no-op: force an explicit Read/Ignore choice")
    override fun onBackPressed() {
        // no-op by design
    }

    companion object {
        const val EXTRA_MESSAGE_ID = "extra_message_id"
        const val EXTRA_SENDER = "extra_sender"
        const val EXTRA_PRIORITY = "extra_priority"
        const val EXTRA_TEXT = "extra_text"
        const val EXTRA_CHAT_ID = "extra_chat_id"
    }
}
