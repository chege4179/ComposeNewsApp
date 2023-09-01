package com.peterchege.composenewsapp.domain.repository.remote

import com.peterchege.composenewsapp.core.api.NetworkResult
import com.peterchege.composenewsapp.core.api.responses.NewsResponse

interface RemoteNewsDataSource {

    suspend fun getTopHeadlineNews(page:Int = 1,limit:Int = 10):NetworkResult<NewsResponse>

}