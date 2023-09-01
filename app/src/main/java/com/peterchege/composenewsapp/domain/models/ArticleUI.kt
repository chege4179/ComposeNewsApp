package com.peterchege.composenewsapp.domain.models

import androidx.room.Entity
import androidx.room.PrimaryKey
import com.peterchege.composenewsapp.core.api.responses.Source


data class ArticleUI(
    var id: Int? ,
    val author: String?,
    val content: String?,
    val description: String?,
    val publishedAt: String?,
    val source: Source?,
    val title: String?,
    val url: String?,
    val urlToImage: String?
)