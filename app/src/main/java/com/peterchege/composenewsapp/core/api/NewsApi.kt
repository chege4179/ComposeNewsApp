/*
 * Copyright 2023 Compose News App By Peter Chege
 *
 * Licensed under the Apache License, Version 2.0 (the "License");
 * you may not use this file except in compliance with the License.
 * You may obtain a copy of the License at
 *
 *      http://www.apache.org/licenses/LICENSE-2.0
 *
 * Unless required by applicable law or agreed to in writing, software
 * distributed under the License is distributed on an "AS IS" BASIS,
 * WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 * See the License for the specific language governing permissions and
 * limitations under the License.
 */
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