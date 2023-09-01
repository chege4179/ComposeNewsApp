package com.peterchege.composenewsapp.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterchege.composenewsapp.core.room.entity.RemoteKeyEntity

@Dao
interface RemoteKeyDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertRemoteKey(item: RemoteKeyEntity)

    @Query("SELECT * FROM remote_key WHERE id = :id")
    suspend fun getRemoteKeyById(id: String): RemoteKeyEntity?

    @Query("DELETE FROM remote_key WHERE id = :id")
    suspend fun deleteRemoteKeyById(id: String)
}