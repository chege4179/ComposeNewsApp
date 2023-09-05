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
package com.peterchege.composenewsapp.core.di

import androidx.paging.ExperimentalPagingApi
import androidx.paging.Pager
import androidx.paging.PagingConfig
import com.peterchege.composenewsapp.core.datastore.DefaultRemoteKeyProvider
import com.peterchege.composenewsapp.core.room.entity.CachedArticleEntity
import com.peterchege.composenewsapp.domain.paging.ArticleRemoteMediator
import com.peterchege.composenewsapp.domain.repository.local.CachedNewsDataSource
import com.peterchege.composenewsapp.domain.repository.remote.RemoteNewsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object PagerModule {
    @OptIn(ExperimentalPagingApi::class)
    @Provides
    @Singleton
    fun provideArticlePager(
        cachedNewsDataSource: CachedNewsDataSource,
        remoteNewsDataSource: RemoteNewsDataSource,
        defaultRemoteKeyProvider: DefaultRemoteKeyProvider,
    ): Pager<Int, CachedArticleEntity> {
        return Pager(
            config = PagingConfig(pageSize = 20),
            remoteMediator = ArticleRemoteMediator(
                cachedNewsDataSource = cachedNewsDataSource,
                remoteNewsDataSource = remoteNewsDataSource,
                defaultRemoteKeyProvider = defaultRemoteKeyProvider
            ),
            pagingSourceFactory = {
                cachedNewsDataSource.getAllCachedNews()
            },
        )
    }
}