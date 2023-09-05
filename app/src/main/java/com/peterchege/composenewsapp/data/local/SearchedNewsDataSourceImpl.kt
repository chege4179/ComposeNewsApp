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
import com.peterchege.composenewsapp.core.room.entity.SearchArticleEntity
import com.peterchege.composenewsapp.domain.repository.local.SearchedNewsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class SearchedNewsDataSourceImpl @Inject constructor(
    private val db:ComposeNewsAppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
):SearchedNewsDataSource {
    override suspend fun insertAllSearchedNews(items: List<SearchArticleEntity>) {
        withContext(ioDispatcher){
            db.searchedNewsDao.insertAllSearchedNews(items = items)
        }
    }

    override fun getAllSearchedNews():Flow<List<SearchArticleEntity>> {
        return db.searchedNewsDao.getAllSearchedNews()
    }

    override fun getSearchNewsById(articleId: Int): Flow<SearchArticleEntity?> {
        return db.searchedNewsDao.getSearchNewsById(articleId).flowOn(ioDispatcher)
    }

    override suspend fun clearAllSearchedNews() {
        withContext(ioDispatcher){
            db.searchedNewsDao.clearAllSearchedNews()
        }
    }


}