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
package com.peterchege.composenewsapp.ui.screens.all_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.peterchege.composenewsapp.core.api.NetworkStatus
import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.core.util.UiEvent
import com.peterchege.composenewsapp.domain.models.ArticleUI
import com.peterchege.composenewsapp.domain.repository.NetworkInfoRepository
import com.peterchege.composenewsapp.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asSharedFlow
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


@HiltViewModel
class AllNewsScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val networkInfoRepository: NetworkInfoRepository,

    ) :ViewModel() {

    val networkStatus = networkInfoRepository.networkStatus
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = NetworkStatus.Unknown
        )

    private val _isRefreshing = MutableStateFlow(false)
    val isRefreshing = _isRefreshing.asStateFlow()

    private val _eventFlow = MutableSharedFlow<UiEvent>()
    val eventFlow = _eventFlow.asSharedFlow()

    val articlesPagingDataFlow: Flow<PagingData<ArticleUI>> =
        newsRepository.getTopHeadlineNews()
        .cachedIn(viewModelScope)

    val bookmarkedArticles = newsRepository.getBookmarkedArticles()
        .onStart { emptyList<ArticleUI>() }
        .catch { emptyList<ArticleUI>() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    fun refreshArticles(){
        viewModelScope.launch {
            _isRefreshing.value = true
            newsRepository.refreshArticles()
            _isRefreshing.value = false
        }
    }


    fun bookmarkedArticle(article:ArticleUI){
        viewModelScope.launch {
            newsRepository.bookmarkArticle(article)
            _eventFlow.emit(UiEvent.ShowSnackbar(message = "Article added to bookmarks"))
        }
    }
    fun unBookmarkArticle(articleId:Int){
        viewModelScope.launch {
            newsRepository.unBookmarkArticle(articleId)
            _eventFlow.emit(UiEvent.ShowSnackbar(message = "Article removed from bookmarks"))
        }
    }



}