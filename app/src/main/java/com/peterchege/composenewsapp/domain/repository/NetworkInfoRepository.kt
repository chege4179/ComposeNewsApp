package com.peterchege.composenewsapp.domain.repository

import com.peterchege.composenewsapp.core.api.NetworkStatus
import kotlinx.coroutines.flow.Flow


interface NetworkInfoRepository {

    val networkStatus: Flow<NetworkStatus>


}