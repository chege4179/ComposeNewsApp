package com.peterchege.composenewsapp.core.room.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import com.peterchege.composenewsapp.core.room.converters.SourceTypeConverter
import com.peterchege.composenewsapp.core.room.dao.BookmarkedNewsDao
import com.peterchege.composenewsapp.core.room.dao.CachedNewsDao
import com.peterchege.composenewsapp.core.room.dao.RemoteKeyDao
import com.peterchege.composenewsapp.core.room.entity.BookmarkArticleEntity
import com.peterchege.composenewsapp.core.room.entity.CachedArticleEntity
import com.peterchege.composenewsapp.core.room.entity.RemoteKeyEntity


@Database(
    entities = [
        BookmarkArticleEntity::class,
        CachedArticleEntity::class,
        RemoteKeyEntity::class,
    ],
    version = 1,
    exportSchema = true,
)
@TypeConverters(SourceTypeConverter::class)
abstract class ComposeNewsAppDatabase : RoomDatabase() {

    abstract val bookmarkNewsDao: BookmarkedNewsDao

    abstract val cachedNewsDao: CachedNewsDao

    abstract val remoteKeyDao: RemoteKeyDao


}