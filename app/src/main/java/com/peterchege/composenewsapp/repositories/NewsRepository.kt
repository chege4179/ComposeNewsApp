package com.peterchege.composenewsapp.repositories

import com.peterchege.composenewsapp.api.NewsApi
import com.peterchege.composenewsapp.models.NewsResponse
import javax.inject.Inject

class NewsRepository @Inject constructor(
    private val api: NewsApi
) {
    suspend fun getTopHeadlines():NewsResponse{
        return api.getTopHeadlines()
    }
}