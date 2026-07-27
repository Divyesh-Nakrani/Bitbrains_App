package com.bitcoding.bitbrains.work

import android.content.Context
import androidx.work.BackoffPolicy
import androidx.work.Constraints
import androidx.work.CoroutineWorker
import androidx.work.ExistingWorkPolicy
import androidx.work.NetworkType
import androidx.work.OneTimeWorkRequestBuilder
import androidx.work.WorkManager
import androidx.work.WorkerParameters
import com.bitcoding.bitbrains.di.ServiceLocator
import java.util.concurrent.TimeUnit

/**
 * Retries device registration until it succeeds (§9, §8.4). Idempotent per FCM
 * token, so it is safe to enqueue on every cold start, on onNewToken, and right
 * after login.
 */
class RegisterDeviceWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        ServiceLocator.ensure(applicationContext)
        val store = ServiceLocator.secureStore

        // Nothing to register until we have both an email (from login/profile)
        // and an FCM token. Not an error — just succeed and wait to be re-enqueued.
        if (store.email.isNullOrBlank() || store.fcmToken.isNullOrBlank()) {
            return Result.success()
        }

        val response = ServiceLocator.deviceRepository.register()
            ?: return Result.retry()   // transport failure

        // A real answer (linked true/false) is terminal — success either way;
        // the UI reads store.linked to decide whether to show the finish-login banner.
        return if (response.success || response.deviceId != null) {
            Result.success()
        } else {
            Result.retry()
        }
    }

    companion object {
        private const val NAME = "register-device"

        fun enqueue(context: Context) {
            val request = OneTimeWorkRequestBuilder<RegisterDeviceWorker>()
                .setConstraints(
                    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                )
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 10, TimeUnit.SECONDS)
                .build()
            WorkManager.getInstance(context)
                .enqueueUniqueWork(NAME, ExistingWorkPolicy.REPLACE, request)
        }
    }
}
