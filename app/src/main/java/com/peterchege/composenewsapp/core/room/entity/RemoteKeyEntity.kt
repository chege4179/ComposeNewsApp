package com.peterchege.composenewsapp.core.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey

@Entity(tableName = "remote_key")
data class RemoteKeyEntity(
    @PrimaryKey val id: String,
    val nextOffset: Int,
)