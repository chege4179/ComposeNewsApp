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
package com.peterchege.composenewsapp.domain.paging

import androidx.paging.ExperimentalPagingApi
import androidx.paging.LoadType
import androidx.paging.PagingState
import androidx.paging.RemoteMediator
import com.peterchege.composenewsapp.core.api.NetworkResult
import com.peterchege.composenewsapp.core.datastore.DefaultRemoteKeyProvider
import com.peterchege.composenewsapp.core.room.database.ComposeNewsAppDatabase
import com.peterchege.composenewsapp.core.room.entity.CachedArticleEntity
import com.peterchege.composenewsapp.domain.mappers.toCacheEntity
import com.peterchege.composenewsapp.domain.repository.local.CachedNewsDataSource
import com.peterchege.composenewsapp.domain.repository.remote.RemoteNewsDataSource
import kotlinx.coroutines.flow.first
import javax.inject.Inject

@OptIn(ExperimentalPagingApi::class)
class ArticleRemoteMediator @Inject constructor(
    private val cachedNewsDataSource: CachedNewsDataSource,
    private val remoteNewsDataSource: RemoteNewsDataSource,
    private val defaultRemoteKeyProvider: DefaultRemoteKeyProvider,
) : RemoteMediator<Int, CachedArticleEntity>() {


    override suspend fun load(loadType: LoadType, state: PagingState<Int, CachedArticleEntity>,): MediatorResult {
        val offset = when (loadType) {
            LoadType.REFRESH -> 1
            LoadType.PREPEND -> return MediatorResult.Success(endOfPaginationReached = true)
            LoadType.APPEND -> {
                // RETRIEVE NEXT OFFSET FROM DATABASE
                val remoteKey = defaultRemoteKeyProvider.getRemoteKey().first()
                remoteKey

            }
        }
        // MAKE API CALL
        val apiResponse = remoteNewsDataSource.getTopHeadlineNews(page = offset, limit = state.config.pageSize)
        println("Api Response : $apiResponse")
        return when(apiResponse){

            is NetworkResult.Success -> {
                val results = apiResponse.data.networkArticles
                val nextOffset = offset + 1
                // SAVE RESULTS AND NEXT OFFSET TO DATABASE
                if (loadType == LoadType.REFRESH) {
                    println("Refresh ${results.size}")
                    cachedNewsDataSource.clearAllCachedNews()
                    defaultRemoteKeyProvider.setRemoteKey(1)

                }
                cachedNewsDataSource.insertAllCachedNews(items = results.map { it.toCacheEntity() })
                defaultRemoteKeyProvider.setRemoteKey(nextOffset)
                // CHECK IF END OF PAGINATION REACHED
                MediatorResult.Success(endOfPaginationReached = results.isEmpty())
            }
            is NetworkResult.Error -> {
                MediatorResult.Error(apiResponse.throwable)
            }
            is NetworkResult.Exception -> {
                MediatorResult.Error(apiResponse.throwable)
            }
        }

    }
}