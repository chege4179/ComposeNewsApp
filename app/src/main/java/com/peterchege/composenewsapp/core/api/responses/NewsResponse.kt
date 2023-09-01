package com.peterchege.composenewsapp.core.api.responses

import kotlinx.serialization.Serializable

@Serializable
data class NewsResponse(
    val networkArticles: List<NetworkArticle>,
    val status: String = "",
    val totalResults: Int = 0
)