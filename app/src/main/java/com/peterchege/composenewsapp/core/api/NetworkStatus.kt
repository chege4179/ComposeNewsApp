package com.peterchege.composenewsapp.core.api

sealed class NetworkStatus {
    object Unknown: NetworkStatus()
    object Connected: NetworkStatus()
    object Disconnected: NetworkStatus()
}