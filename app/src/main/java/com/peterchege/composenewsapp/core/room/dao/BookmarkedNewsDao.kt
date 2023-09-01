package com.peterchege.composenewsapp.core.room.dao

import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterchege.composenewsapp.core.api.responses.NetworkArticle
import com.peterchege.composenewsapp.core.room.entity.BookmarkArticleEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface BookmarkedNewsDao {
    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertBookmarkedNews(bookmarkArticleEntity: BookmarkArticleEntity)
    @Query("SELECT * FROM bookmarked_articles")
    fun getBookmarkedNewsArticles(): Flow<List<NetworkArticle>>

    @Query("SELECT * FROM bookmarked_articles WHERE id = :articleId")
    fun getBookmarkedNewsArticleById(articleId:Int): Flow<NetworkArticle?>

    @Query("DELETE FROM bookmarked_articles WHERE id = :articleId")
    suspend fun deleteBookmarkedArticleById(articleId: Int)



}