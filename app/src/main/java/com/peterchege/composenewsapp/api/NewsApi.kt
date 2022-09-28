package com.peterchege.composenewsapp.api

import android.provider.SyncStateContract
import com.peterchege.composenewsapp.models.NewsResponse
import com.peterchege.composenewsapp.util.Constants
import com.peterchege.composenewsapp.util.Constants.BASE_URL
import okhttp3.MultipartBody
import retrofit2.Retrofit
import retrofit2.converter.gson.GsonConverterFactory
import retrofit2.http.*

interface NewsApi {
    @GET("top-headlines")
    suspend fun getTopHeadlines(
        @Query("country") country:String = "us",
        @Query("apiKey") apiKey :String = Constants.API_KEY
    ): NewsResponse

}