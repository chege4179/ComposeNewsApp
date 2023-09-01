package com.peterchege.composenewsapp.domain.repository.local

import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import kotlinx.coroutines.flow.Flow

interface BookmarkedNewsDataSource {

    suspend fun insertBookmarkedNews(networkArticle: NetworkArticle)

    fun getAllBookmarkedArticles(): Flow<List<NetworkArticle>>

    fun getBookmarkedArticleById(articleId:Int):Flow<NetworkArticle?>

    suspend fun deleteBookmarkedArticle(articleId:Int)

}