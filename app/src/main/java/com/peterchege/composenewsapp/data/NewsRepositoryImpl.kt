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
package com.peterchege.composenewsapp.data

import androidx.paging.Pager
import androidx.paging.PagingData
import androidx.paging.map
import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.core.di.IoDispatcher
import com.peterchege.composenewsapp.core.room.entity.CachedArticleEntity
import com.peterchege.composenewsapp.domain.mappers.toExternalModel
import com.peterchege.composenewsapp.domain.mappers.toPresentationModel
import com.peterchege.composenewsapp.domain.models.ArticleUI
import com.peterchege.composenewsapp.domain.repository.NewsRepository
import com.peterchege.composenewsapp.domain.repository.local.CachedNewsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class NewsRepositoryImpl @Inject constructor(
    private val newsPager: Pager<Int, CachedArticleEntity>,
    private val cachedNewsDataSource: CachedNewsDataSource,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
) : NewsRepository {

    override fun getTopHeadlineNews(): Flow<PagingData<ArticleUI>> {
        return newsPager.flow.map { pagingData ->
            pagingData.map { it.toPresentationModel() }
        }.flowOn(ioDispatcher)
    }

    override fun getArticleById(articleId: Int): Flow<ArticleUI?> {
        return cachedNewsDataSource.getCachedNewsById(articleId)
            .map { it?.toPresentationModel() }
    }

}