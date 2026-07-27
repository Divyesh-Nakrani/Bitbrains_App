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
import androidx.work.workDataOf
import com.bitcoding.bitbrains.data.repository.AlertRepository.ActionOutcome
import com.bitcoding.bitbrains.di.ServiceLocator
import java.util.concurrent.TimeUnit

/**
 * Reports READ/IGNORED to the backend and retries until a 200 (§8.6). The phone
 * may be offline exactly when the alert fires, so this must survive process death
 * — hence WorkManager, not a fire-and-forget coroutine.
 */
class ReportActionWorker(
    context: Context,
    params: WorkerParameters
) : CoroutineWorker(context, params) {

    override suspend fun doWork(): Result {
        ServiceLocator.ensure(applicationContext)
        val messageId = inputData.getString(KEY_MESSAGE_ID) ?: return Result.failure()
        val action = inputData.getString(KEY_ACTION) ?: return Result.failure()
        val timestamp = inputData.getLong(KEY_TIMESTAMP, System.currentTimeMillis())

        val repo = ServiceLocator.alertRepository
        repo.recordChoiceLocally(messageId, action)

        return when (repo.reportAction(messageId, action, timestamp)) {
            ActionOutcome.Success -> Result.success()
            ActionOutcome.Terminal -> Result.success()   // 400/404/422 — retrying won't help
            ActionOutcome.Retry -> Result.retry()        // 5xx / timeout / offline
        }
    }

    companion object {
        private const val KEY_MESSAGE_ID = "messageId"
        private const val KEY_ACTION = "action"
        private const val KEY_TIMESTAMP = "timestamp"

        fun enqueue(context: Context, messageId: String, action: String) {
            val request = OneTimeWorkRequestBuilder<ReportActionWorker>()
                .setInputData(
                    workDataOf(
                        KEY_MESSAGE_ID to messageId,
                        KEY_ACTION to action,
                        KEY_TIMESTAMP to System.currentTimeMillis()
                    )
                )
                .setConstraints(
                    Constraints.Builder().setRequiredNetworkType(NetworkType.CONNECTED).build()
                )
                .setBackoffCriteria(BackoffPolicy.EXPONENTIAL, 10, TimeUnit.SECONDS)
                .build()
            // Unique per (message, action) so a Read and a later Ignore don't clobber.
            WorkManager.getInstance(context).enqueueUniqueWork(
                "action-$messageId-$action",
                ExistingWorkPolicy.KEEP,
                request
            )
        }
    }
}
