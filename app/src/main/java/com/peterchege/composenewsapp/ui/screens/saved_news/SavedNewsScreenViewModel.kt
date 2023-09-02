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
package com.peterchege.composenewsapp.ui.screens.saved_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.composenewsapp.domain.models.ArticleUI
import com.peterchege.composenewsapp.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.onStart
import kotlinx.coroutines.flow.stateIn
import kotlinx.coroutines.launch
import javax.inject.Inject

sealed interface SavedNewsScreenUiState {
    object Loading : SavedNewsScreenUiState

    data class Success(val articles: List<ArticleUI>) : SavedNewsScreenUiState

    data class Error(val message: String) : SavedNewsScreenUiState
}

@HiltViewModel
class SavedNewsScreenViewModel @Inject constructor(
    private val repository: NewsRepository,
) :ViewModel(){

    val uiState = repository.getBookmarkedArticles()
        .map(SavedNewsScreenUiState::Success)
        .onStart { SavedNewsScreenUiState.Loading }
        .catch { SavedNewsScreenUiState.Error(message = "An unexpected error") }
        .stateIn(
            scope = viewModelScope,
            started = SharingStarted.WhileSubscribed(5000L),
            initialValue = SavedNewsScreenUiState.Loading
        )

    fun unBookmarkArticle(articleId:Int){
        viewModelScope.launch {
            repository.unBookmarkArticle(articleId)
        }
    }

}