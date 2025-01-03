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
package com.peterchege.composenewsapp.ui.screens.single_news

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.core.util.Constants
import com.peterchege.composenewsapp.domain.models.ArticleUI
import com.peterchege.composenewsapp.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import javax.inject.Inject


sealed interface SingleNewsScreenUiState {
    object Loading : SingleNewsScreenUiState

    data class Success(val article: ArticleUI) : SingleNewsScreenUiState

    data class Error(val message: String) : SingleNewsScreenUiState
}

@HiltViewModel
class SingleNewsScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository,
    private val savedStateHandle: SavedStateHandle,
):ViewModel(){
    private val articleId = savedStateHandle.getStateFlow(key = "articleId",initialValue =0)
    private val sourceFlow = savedStateHandle.getStateFlow(key="source", initialValue = "")

    // check for the article from all 3 sources
    val uiState = combine(
        newsRepository.getArticleById(articleId.value),
        newsRepository.getBookmarkedArticleById(articleId.value),
        newsRepository.getSearchNewsArticleById(articleId.value),
        sourceFlow
    ){ feedArticle,bookmarkedArticle,searchArticle,source ->
        when(source){
            // ordering the search of articles based of the previous screen for correct article
            Constants.ALL_NEW ->searchArticle ?: feedArticle?: bookmarkedArticle
            Constants.SAVED -> bookmarkedArticle?:searchArticle?:feedArticle
            Constants.SEARCH -> searchArticle?: feedArticle?:bookmarkedArticle
            else -> feedArticle?: bookmarkedArticle
        }
    }
        .map { article ->
            if (article == null){
                SingleNewsScreenUiState.Error("Article not found")
            }else{
                SingleNewsScreenUiState.Success(article = article)
            }
        }
        .onStart { SingleNewsScreenUiState.Loading }
        .catch { SingleNewsScreenUiState.Error(message ="An unexpected error occurred") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = SingleNewsScreenUiState.Loading
        )


}