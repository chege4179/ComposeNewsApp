package com.peterchege.composenewsapp.core.room.dao

import androidx.paging.PagingSource
import androidx.room.Dao
import androidx.room.Insert
import androidx.room.OnConflictStrategy
import androidx.room.Query
import com.peterchege.composenewsapp.core.room.entity.CachedArticleEntity
import kotlinx.coroutines.flow.Flow


@Dao
interface CachedNewsDao {

    @Insert(onConflict = OnConflictStrategy.REPLACE)
    suspend fun insertAllCachedNews(items: List<CachedArticleEntity>)

    @Query("SELECT * FROM cached_articles")
    fun getAllCachedNews(): PagingSource<Int, CachedArticleEntity>

    @Query("SELECT * FROM cached_articles WHERE id = :articleId")
    fun getCachedNewsById(articleId:Int): Flow<CachedArticleEntity?>

    @Query("DELETE FROM cached_articles")
    suspend fun clearAllCachedNews()
}