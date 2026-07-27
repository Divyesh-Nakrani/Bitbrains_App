package com.bitcoding.bitbrains.ui.login

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.browser.customtabs.CustomTabsIntent
import com.bitcoding.bitbrains.BuildConfig
import com.bitcoding.bitbrains.databinding.ActivityLoginBinding
import com.bitcoding.bitbrains.di.ServiceLocator
import com.bitcoding.bitbrains.ui.home.HomeActivity
import com.bitcoding.bitbrains.ui.settings.DiagnosticsActivity

/**
 * Launcher / sign-in screen. This screen — and the "Sign in with Microsoft"
 * button — is only ever shown when there is NO session. If a token already
 * exists we jump straight to the dashboard, which surfaces the authenticated
 * state (Home re-validates the token and bounces back here on a 401).
 *
 * Alert permissions are handled on the dashboard after sign-in, so this screen
 * stays focused on the one action a signed-out user can take.
 */
class LoginActivity : AppCompatActivity() {

    private lateinit var binding: ActivityLoginBinding

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        if (ServiceLocator.secureStore.isLoggedIn) {
            goHome()
            return
        }

        binding = ActivityLoginBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.buttonSignIn.setOnClickListener { startMicrosoftLogin() }
        binding.buttonDiagnostics.setOnClickListener {
            startActivity(Intent(this, DiagnosticsActivity::class.java))
        }
    }

    override fun onResume() {
        super.onResume()
        // A token may have arrived via the deep-link callback while we were paused.
        if (ServiceLocator.secureStore.isLoggedIn) goHome()
    }

    /** §6.3 — /login is a 302 to Microsoft; open it in a Custom Tab, never Retrofit. */
    private fun startMicrosoftLogin() {
        val url = BuildConfig.API_BASE_URL.trimEnd('/') + "/login"
        CustomTabsIntent.Builder().build().launchUrl(this, Uri.parse(url))
    }

    private fun goHome() {
        startActivity(Intent(this, HomeActivity::class.java))
        finish()
    }
}
