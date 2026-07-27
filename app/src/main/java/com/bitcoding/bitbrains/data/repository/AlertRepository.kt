package com.bitcoding.bitbrains.data.repository

import android.util.Log
import com.bitcoding.bitbrains.data.local.AlertDao
import com.bitcoding.bitbrains.data.local.AlertEntity
import com.bitcoding.bitbrains.data.local.SecureStore
import com.bitcoding.bitbrains.data.remote.ApiClient
import com.bitcoding.bitbrains.data.remote.dto.MessageActionRequest

/**
 * Owns the received-alert history (Room) and the READ/IGNORED reporting call
 * (§6.7). The action call is the most important in the app, so its outcome is
 * modelled explicitly: [ActionOutcome] tells the Worker whether to retry.
 */
class AlertRepository(
    private val api: ApiClient,
    private val dao: AlertDao,
    private val store: SecureStore
) {

    /** true if this messageId was newly inserted; false if it was a duplicate push. */
    suspend fun persist(alert: AlertEntity): Boolean = dao.insert(alert) != -1L

    suspend fun recent(limit: Int = 50): List<AlertEntity> = dao.recent(limit)

    /** Record the user's choice locally before the network call (survives death). */
    suspend fun recordChoiceLocally(messageId: String, action: String) =
        dao.setAction(messageId, action, synced = false)

    /**
     * Report READ/IGNORED to the backend (§6.7). Semantics that matter:
     * - 200 with resolved=false is still SUCCESS (e.g. a dev.test push). Never retry it.
     * - 400/404/422 are terminal request problems — do not retry.
     * - 5xx / timeout / offline are retryable.
     */
    suspend fun reportAction(
        messageId: String,
        action: String,
        actionTimestamp: Long
    ): ActionOutcome {
        val body = MessageActionRequest(
            action = action,
            employeeId = store.email,          // resolves the user if no bearer
            actionTimestamp = actionTimestamp,
            fcmToken = store.fcmToken           // attributes to this install
        )
        return runCatching {
            val resp = api.service.postMessageAction(messageId, body)
            when {
                resp.isSuccessful -> {
                    dao.markSynced(messageId)
                    ActionOutcome.Success
                }
                resp.code() in listOf(400, 404, 422) -> {
                    Log.w(TAG, "action $action for $messageId terminal ${resp.code()}")
                    ActionOutcome.Terminal
                }
                else -> ActionOutcome.Retry   // 401/5xx/etc.
            }
        }.getOrElse {
            Log.w(TAG, "action $action for $messageId failed: ${it.message}")
            ActionOutcome.Retry               // timeout / offline
        }
    }

    enum class ActionOutcome { Success, Terminal, Retry }

    companion object {
        private const val TAG = "AlertRepository"
    }
}
