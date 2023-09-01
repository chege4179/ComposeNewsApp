package com.peterchege.composenewsapp.ui.screens.single_news

import androidx.lifecycle.SavedStateHandle
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.domain.models.ArticleUI
import com.peterchege.composenewsapp.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.SharingStarted
import kotlinx.coroutines.flow.catch
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


    val uiState = newsRepository.getArticleById(articleId.value)
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