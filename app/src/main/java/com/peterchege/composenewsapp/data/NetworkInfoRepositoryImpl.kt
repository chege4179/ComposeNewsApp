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
package com.peterchege.composenewsapp.data

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkRequest
import androidx.core.content.getSystemService
import com.peterchege.composenewsapp.core.api.NetworkStatus
import com.peterchege.composenewsapp.core.di.IoDispatcher
import com.peterchege.composenewsapp.domain.repository.NetworkInfoRepository
import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.channels.awaitClose
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.callbackFlow
import kotlinx.coroutines.flow.conflate
import kotlinx.coroutines.flow.distinctUntilChanged
import kotlinx.coroutines.flow.flowOn
import javax.inject.Inject

class NetworkInfoRepositoryImpl @Inject constructor(
    context: Context,
    @IoDispatcher private val ioDispatcher: CoroutineDispatcher,
):NetworkInfoRepository {

    private val connectivityManager = context.getSystemService<ConnectivityManager>()

    override val networkStatus: Flow<NetworkStatus> = callbackFlow {
        if (connectivityManager == null) {
            channel.trySend(NetworkStatus.Unknown)
            channel.close()
            return@callbackFlow
        }
        val connectivityCallback = object : ConnectivityManager.NetworkCallback() {
            override fun onAvailable(network: Network) {
                trySend(element = NetworkStatus.Connected)
            }

            override fun onUnavailable() {
                trySend(element = NetworkStatus.Disconnected)
            }

            override fun onLost(network: Network) {
                trySend(element = NetworkStatus.Disconnected)
            }

        }

        val request = NetworkRequest.Builder()
            .addCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
            .addTransportType(NetworkCapabilities.TRANSPORT_WIFI)
            .addTransportType(NetworkCapabilities.TRANSPORT_CELLULAR)
            .build()


        connectivityManager.registerNetworkCallback(request, connectivityCallback)

        awaitClose {
            connectivityManager.unregisterNetworkCallback(connectivityCallback)
        }
    }
        .distinctUntilChanged()
        .flowOn(context = ioDispatcher)
        .conflate()
}