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