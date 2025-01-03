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
package com.peterchege.composenewsapp.domain.repository

import androidx.paging.PagingData
import com.peterchege.composenewsapp.core.api.NetworkResult
import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.core.api.responses.NewsResponse
import com.peterchege.composenewsapp.core.room.entity.SearchArticleEntity
import com.peterchege.composenewsapp.domain.models.ArticleUI
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getTopHeadlineNews(): Flow<PagingData<ArticleUI>>

    fun getArticleById(articleId: Int):Flow<ArticleUI?>

    fun getBookmarkedArticles():Flow<List<ArticleUI>>

    fun getSearchArticles():Flow<List<ArticleUI>>

    suspend fun insertCachedSearchedNews(articles:List<NetworkArticle>)

    suspend fun deleteSearchedNews()

    fun getBookmarkedArticleById(articleId: Int):Flow<ArticleUI?>

    fun getSearchNewsArticleById(articleId: Int):Flow<ArticleUI?>

    suspend fun bookmarkArticle(articleUI: ArticleUI)

    suspend fun unBookmarkArticle(articleId:Int)

    suspend fun searchArticles(query:String):NetworkResult<NewsResponse>

    suspend fun refreshArticles()
}