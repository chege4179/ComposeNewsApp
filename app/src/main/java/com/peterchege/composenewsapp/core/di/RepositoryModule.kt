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

import androidx.paging.Pager
import com.peterchege.composenewsapp.core.api.NewsApi
import com.peterchege.composenewsapp.core.room.database.ComposeNewsAppDatabase
import com.peterchege.composenewsapp.core.room.entity.CachedArticleEntity
import com.peterchege.composenewsapp.data.NewsRepositoryImpl
import com.peterchege.composenewsapp.data.local.BookmarkedNewsDataSourceImpl
import com.peterchege.composenewsapp.data.local.CachedNewsDataSourceImpl
import com.peterchege.composenewsapp.data.remote.RemoteNewsDataSourceImpl
import com.peterchege.composenewsapp.domain.repository.NewsRepository
import com.peterchege.composenewsapp.domain.repository.local.BookmarkedNewsDataSource
import com.peterchege.composenewsapp.domain.repository.local.CachedNewsDataSource
import com.peterchege.composenewsapp.domain.repository.remote.RemoteNewsDataSource
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import kotlinx.coroutines.CoroutineDispatcher
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoryModule {

    @Provides
    @Singleton
    fun provideNewsRepository(
        newsPager: Pager<Int, CachedArticleEntity>,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
        cachedNewsDataSource: CachedNewsDataSource,
    ): NewsRepository {
        return NewsRepositoryImpl(
            newsPager = newsPager,
            ioDispatcher = ioDispatcher,
            cachedNewsDataSource = cachedNewsDataSource,
        )
    }

    @Provides
    @Singleton
    fun provideCachedNewsDataSource(
        db:ComposeNewsAppDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): CachedNewsDataSource {
        return CachedNewsDataSourceImpl(db  = db, ioDispatcher = ioDispatcher)
    }
    @Provides
    @Singleton
    fun provideBookmarkedNewsDataSource(
        db:ComposeNewsAppDatabase,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): BookmarkedNewsDataSource {
        return BookmarkedNewsDataSourceImpl(db  = db, ioDispatcher = ioDispatcher)
    }


    @Provides
    @Singleton
    fun provideRemoteNewsDataSource(
        api:NewsApi,
        @IoDispatcher ioDispatcher: CoroutineDispatcher,
    ): RemoteNewsDataSource {
        return RemoteNewsDataSourceImpl(api = api, ioDispatcher = ioDispatcher)
    }

}