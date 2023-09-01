/*
 * Copyright 2023 Compose News App By Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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