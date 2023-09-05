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
package com.peterchege.composenewsapp.data.remote

import com.peterchege.composenewsapp.core.api.NetworkResult
import com.peterchege.composenewsapp.core.api.NewsApi
import com.peterchege.composenewsapp.core.api.responses.NewsResponse
import com.peterchege.composenewsapp.core.api.safeApiCall
import com.peterchege.composenewsapp.core.di.IoDispatcher
import com.peterchege.composenewsapp.domain.repository.remote.RemoteNewsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteNewsDataSourceImpl @Inject constructor(
    private val api: NewsApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
):RemoteNewsDataSource {

    override suspend fun getTopHeadlineNews(page: Int, limit: Int): NetworkResult<NewsResponse> {
        return withContext(ioDispatcher){
            safeApiCall { api.getTopHeadlines(page = page, pageSize = limit) }
        }
    }

    override suspend fun searchNewsArticles(query: String): NetworkResult<NewsResponse> {
        return withContext(ioDispatcher){
            safeApiCall { api.searchNewsArticles(query = query) }
        }
    }
}