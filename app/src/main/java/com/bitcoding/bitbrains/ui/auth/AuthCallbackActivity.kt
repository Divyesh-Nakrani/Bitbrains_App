package com.bitcoding.bitbrains.ui.auth

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import com.bitcoding.bitbrains.di.ServiceLocator
import com.bitcoding.bitbrains.ui.home.HomeActivity
import com.bitcoding.bitbrains.ui.login.LoginActivity

/**
 * Handles the Microsoft login redirect (§6.3, §8.2):
 *   bitbrains://auth/callback?login=success&token=<JWT>
 *   bitbrains://auth/callback?login=error&message=<reason>
 *
 * On success: store the token encrypted and continue to Home (which fetches the
 * profile email and registers the device). singleTask, so also handle onNewIntent.
 */
class AuthCallbackActivity : AppCompatActivity() {

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        handle(intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        handle(intent)
    }

    private fun handle(intent: Intent?) {
        val data: Uri? = intent?.data
        val login = data?.getQueryParameter("login")
        val token = data?.getQueryParameter("token")

        when {
            login == "success" && !token.isNullOrBlank() -> {
                ServiceLocator.secureStore.sessionToken = token
                startActivity(
                    Intent(this, HomeActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP or Intent.FLAG_ACTIVITY_SINGLE_TOP)
                )
            }
            else -> {
                val message = data?.getQueryParameter("message") ?: "Login failed. Please try again."
                Toast.makeText(this, message, Toast.LENGTH_LONG).show()
                startActivity(
                    Intent(this, LoginActivity::class.java)
                        .addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP)
                )
            }
        }
        finish()
    }
}
