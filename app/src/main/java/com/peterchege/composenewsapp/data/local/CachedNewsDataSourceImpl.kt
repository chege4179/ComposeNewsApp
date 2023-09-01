package com.peterchege.composenewsapp.data.local

import androidx.paging.PagingSource
import com.peterchege.composenewsapp.core.di.IoDispatcher
import com.peterchege.composenewsapp.core.room.database.ComposeNewsAppDatabase
import com.peterchege.composenewsapp.core.room.entity.CachedArticleEntity
import com.peterchege.composenewsapp.domain.repository.local.CachedNewsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class CachedNewsDataSourceImpl @Inject constructor(
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
    private val db:ComposeNewsAppDatabase,
):CachedNewsDataSource {
    override fun getCachedNewsById(articleId: Int): Flow<CachedArticleEntity?> {
        return db.cachedNewsDao.getCachedNewsById(articleId)
            .flowOn(ioDispatcher)
    }
    override suspend fun insertAllCachedNews(items: List<CachedArticleEntity>) {
        withContext(ioDispatcher){
            db.cachedNewsDao.insertAllCachedNews(items)
        }
    }

    override fun getAllCachedNews(): PagingSource<Int, CachedArticleEntity> {
        return db.cachedNewsDao.getAllCachedNews()
    }

    override suspend fun clearAllCachedNews() {
        withContext(ioDispatcher){
            db.cachedNewsDao.clearAllCachedNews()
        }
    }


}