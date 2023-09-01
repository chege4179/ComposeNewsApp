package com.peterchege.composenewsapp.ui.screens.all_news

import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import androidx.paging.PagingData
import androidx.paging.cachedIn
import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.domain.models.ArticleUI
import com.peterchege.composenewsapp.domain.repository.NewsRepository
import dagger.hilt.android.lifecycle.HiltViewModel
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject


@HiltViewModel
class AllNewsScreenViewModel @Inject constructor(
    private val newsRepository: NewsRepository,

    ) :ViewModel() {


    val articlesPagingDataFlow: Flow<PagingData<ArticleUI>> =
        newsRepository.getTopHeadlineNews()
        .cachedIn(viewModelScope)



}