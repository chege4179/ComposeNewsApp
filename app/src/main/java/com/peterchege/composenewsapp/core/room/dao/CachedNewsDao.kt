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
package com.peterchege.composenewsapp.core.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterchege.composenewsapp.core.room.entity.CachedArticleEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CachedNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCachedNews(items: List<CachedArticleEntity>)

    @Query("SELECT * FROM cached_articles")
    fun getAllCachedNews(): PagingSource<Int, CachedArticleEntity>

    @Query("SELECT * FROM cached_articles WHERE id = :articleId")
    fun getCachedNewsById(articleId:Int): Flow<CachedArticleEntity?>

    @Query("DELETE FROM cached_articles")
    suspend fun clearAllCachedNews()
}