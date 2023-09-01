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