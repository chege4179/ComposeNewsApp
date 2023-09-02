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
package com.peterchege.composenewsapp.domain.repository.local

import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.core.room.entity.BookmarkArticleEntity
import kotlinx.coroutines.flow.Flow

interface BookmarkedNewsDataSource {

    suspend fun insertBookmarkedNews(networkArticle: NetworkArticle)

    fun getAllBookmarkedArticles(): Flow<List<BookmarkArticleEntity>>

    fun getBookmarkedArticleById(articleId:Int):Flow<BookmarkArticleEntity?>

    suspend fun deleteBookmarkedArticle(articleId:Int)

}