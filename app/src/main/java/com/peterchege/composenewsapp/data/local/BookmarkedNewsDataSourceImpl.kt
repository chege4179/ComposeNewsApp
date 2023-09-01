package com.peterchege.composenewsapp.data.local

import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.core.di.IoDispatcher
import com.peterchege.composenewsapp.core.room.database.ComposeNewsAppDatabase
import com.peterchege.composenewsapp.domain.mappers.toBookmarkEntity
import com.peterchege.composenewsapp.domain.repository.local.BookmarkedNewsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.withContext
import javax.inject.Inject

class BookmarkedNewsDataSourceImpl @Inject constructor(
    private val db:ComposeNewsAppDatabase,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
):BookmarkedNewsDataSource {
    override suspend fun insertBookmarkedNews(networkArticle: NetworkArticle) {
        withContext(ioDispatcher){
            db.bookmarkNewsDao.insertBookmarkedNews(bookmarkArticleEntity = networkArticle.toBookmarkEntity())
        }
    }

    override fun getAllBookmarkedArticles(): Flow<List<NetworkArticle>> {
        return db.bookmarkNewsDao.getBookmarkedNewsArticles().flowOn(ioDispatcher)
    }

    override fun getBookmarkedArticleById(articleId: Int): Flow<NetworkArticle?> {
        return db.bookmarkNewsDao.getBookmarkedNewsArticleById(articleId).flowOn(ioDispatcher)
    }

    override suspend fun deleteBookmarkedArticle(articleId: Int) {
        withContext(ioDispatcher){
            db.bookmarkNewsDao.deleteBookmarkedArticleById(articleId = articleId)
        }
    }


}