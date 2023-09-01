package com.peterchege.composenewsapp.core.api.responses

import kotlinx.serialization.Serializable


@Serializable
data class NetworkArticle(
    val author: String? ="",
    val content: String? = "",
    val description: String? = "",
    val publishedAt: String = "",
    val source: Source,
    val title: String = "",
    val url: String = "",
    val urlToImage: String? = ""
)