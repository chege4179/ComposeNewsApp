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
package com.peterchege.composenewsapp.ui.screens.search_news

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.composenewsapp.core.api.NetworkResult
import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.core.room.entity.SearchArticleEntity
import com.peterchege.composenewsapp.domain.mappers.toPresentationModel
import com.peterchege.composenewsapp.domain.mappers.toSearchCacheEntity
import com.peterchege.composenewsapp.domain.models.ArticleUI
import com.peterchege.composenewsapp.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.Job
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.asStateFlow
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject


sealed interface SearchNewsScreenUiState {
    object Idle : SearchNewsScreenUiState
    object Searching : SearchNewsScreenUiState

    object ResultsFound:SearchNewsScreenUiState

    data class Error(val message: String) : SearchNewsScreenUiState
}

@HiltViewModel
class SearchNewsScreenViewModel @Inject constructor(
    private val savedStateHandle: SavedStateHandle,
    private val repository: NewsRepository,
) : ViewModel() {

    val searchTerm = savedStateHandle.getStateFlow(key = "query", initialValue = "")

    val searchArticles = repository.getSearchArticles()
        .onStart { emptyList<ArticleUI>() }
        .catch { emptyList<ArticleUI>() }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = emptyList()
        )

    private val _uiState = MutableStateFlow<SearchNewsScreenUiState>(SearchNewsScreenUiState.Idle)
    val uiState = _uiState.asStateFlow()

    private var searchJob: Job? = null
    fun onChangeQuery(query: String) {
        savedStateHandle["query"] = query
        if (query.length > 3) {
            _uiState.value = SearchNewsScreenUiState.Searching
            searchJob?.cancel()
            searchJob = viewModelScope.launch {
                val response = repository.searchArticles(query = query)
                when (response) {
                    is NetworkResult.Success -> {
                        repository.insertCachedSearchedNews(articles = response.data.articles)
                        _uiState.value = SearchNewsScreenUiState.ResultsFound
                    }

                    is NetworkResult.Error -> {
                        _uiState.value =
                            SearchNewsScreenUiState.Error(message = "An unexpected error has occurred")
                    }

                    is NetworkResult.Exception -> {
                        _uiState.value =
                            SearchNewsScreenUiState.Error(message = "An unexpected exception has occurred")
                    }
                }
            }
        } else if (query.length < 2) {
            _uiState.value = SearchNewsScreenUiState.Idle
            viewModelScope.launch {
                delay(500L)
                repository.deleteSearchedNews()
            }

        }
    }

}