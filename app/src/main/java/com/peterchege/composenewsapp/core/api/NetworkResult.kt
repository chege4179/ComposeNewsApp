
package com.peterchege.composenewsapp.core.api

sealed class NetworkResult<T : Any> {
    class Success<T: Any>(val data: T) : NetworkResult<T>()
    class Error<T: Any>(val throwable: Throwable) : NetworkResult<T>()
    class Exception<T: Any>(val throwable: Throwable) : NetworkResult<T>()
}