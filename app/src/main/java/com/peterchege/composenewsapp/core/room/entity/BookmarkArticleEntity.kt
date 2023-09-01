package com.peterchege.composenewsapp.core.room.entity

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.peterchege.composenewsapp.core.api.responses.Source

@Entity(tableName = "bookmarked_articles")
data class BookmarkArticleEntity(
    @PrimaryKey(autoGenerate = true)
    var id: Int? = null,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)