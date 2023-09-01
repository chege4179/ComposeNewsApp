package com.peterchege.composenewsapp.data.remote

import com.peterchege.composenewsapp.core.api.NetworkResult
import com.peterchege.composenewsapp.core.api.NewsApi
import com.peterchege.composenewsapp.core.api.responses.NewsResponse
import com.peterchege.composenewsapp.core.api.safeApiCall
import com.peterchege.composenewsapp.core.di.IoDispatcher
import com.peterchege.composenewsapp.domain.repository.remote.RemoteNewsDataSource
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.withContext
import javax.inject.Inject

class RemoteNewsDataSourceImpl @Inject constructor(
    private val api: NewsApi,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher
):RemoteNewsDataSource {

    override suspend fun getTopHeadlineNews(page: Int, limit: Int): NetworkResult<NewsResponse> {
        return withContext(ioDispatcher){
            safeApiCall { api.getTopHeadlines(page = page, pageSize = limit) }
        }
    }
}