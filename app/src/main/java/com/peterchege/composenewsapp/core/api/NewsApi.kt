package com.peterchege.composenewsapp.core.api

import androidx.compose.foundation.pager.PageSize
import com.peterchege.composenewsapp.core.api.responses.NewsResponse
import com.peterchege.composenewsapp.core.util.Constants
import retrofit2.Response
import retrofit2.http.*

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country:String = "us",
        @Query("apiKey") apiKey :String = Constants.API_KEY,
        @Query("page") page:Int = 1,
        @Query("pageSize") pageSize:Int = 10
    ): Response<NewsResponse>

}