package com.peterchege.composenewsapp.domain.repository

import androidx.paging.PagingData
import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.domain.models.ArticleUI
import kotlinx.coroutines.flow.Flow

interface NewsRepository {

    fun getTopHeadlineNews(): Flow<PagingData<ArticleUI>>

    fun getArticleById(articleId: Int):Flow<ArticleUI?>
}