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

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.core.room.entity.BookmarkArticleEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface BookmarkedNewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkedNews(bookmarkArticleEntity: BookmarkArticleEntity)
    @Query("SELECT * FROM bookmarked_articles")
    fun getBookmarkedNewsArticles(): Flow<List<BookmarkArticleEntity>>

    @Query("SELECT * FROM bookmarked_articles WHERE id = :articleId")
    fun getBookmarkedNewsArticleById(articleId:Int): Flow<BookmarkArticleEntity?>

    @Query("DELETE FROM bookmarked_articles WHERE id = :articleId")
    suspend fun deleteBookmarkedArticleById(articleId: Int)



}