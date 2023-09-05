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
import androidx.room.Transaction
import com.peterchege.composenewsapp.core.room.entity.CachedArticleEntity
import com.peterchege.composenewsapp.core.room.entity.SearchArticleEntity
import kotlinx.coroutines.flow.Flow

@Dao
interface SearchedNewsDao {

    @Transaction
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllSearchedNews(items: List<SearchArticleEntity>)

    @Query("SELECT * FROM searched_articles")
    fun getAllSearchedNews(): Flow<List<SearchArticleEntity>>

    @Query("SELECT * FROM searched_articles WHERE id = :articleId")
    fun getSearchNewsById(articleId:Int): Flow<SearchArticleEntity?>

    @Query("DELETE FROM searched_articles")
    suspend fun clearAllSearchedNews()
}