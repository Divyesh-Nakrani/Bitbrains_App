package com.bitcoding.bitbrains.data.local

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query

@Dao
interface AlertDao {

    /** Persist a freshly-received push. IGNORE keeps the first copy on a duplicate. */
    @Insert(onConflict = OnConflictStrategy.IGNORE)
    suspend fun insert(alert: AlertEntity): Long

    @Query("SELECT * FROM alerts WHERE messageId = :messageId LIMIT 1")
    suspend fun findById(messageId: String): AlertEntity?

    @Query("SELECT * FROM alerts ORDER BY receivedAtLocal DESC LIMIT :limit")
    suspend fun recent(limit: Int = 50): List<AlertEntity>

    /** Record the user's choice locally; actionSynced flips to true only on a 200. */
    @Query("UPDATE alerts SET action = :action, actionSynced = :synced WHERE messageId = :messageId")
    suspend fun setAction(messageId: String, action: String, synced: Boolean)

    @Query("UPDATE alerts SET actionSynced = 1 WHERE messageId = :messageId")
    suspend fun markSynced(messageId: String)
}
