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