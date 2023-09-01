package com.peterchege.composenewsapp.domain.repository.local

import androidx.paging.PagingSource
import com.peterchege.composenewsapp.core.room.entity.CachedArticleEntity
import kotlinx.coroutines.flow.Flow

interface CachedNewsDataSource {

    suspend fun insertAllCachedNews(items: List<CachedArticleEntity>)

    fun getAllCachedNews(): PagingSource<Int, CachedArticleEntity>

    fun getCachedNewsById(articleId:Int): Flow<CachedArticleEntity?>

    suspend fun clearAllCachedNews()



}