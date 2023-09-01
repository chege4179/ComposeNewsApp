
package com.peterchege.composenewsapp.core.api

import retrofit2.HttpException
import retrofit2.Response


suspend fun <T : Any> safeApiCall(
    execute: suspend () -> Response<T>
): NetworkResult<T> {
    return try {
        val response = execute()
        val body = response.body()
        if (response.isSuccessful && body != null) {
            NetworkResult.Success(body)
        } else {
            NetworkResult.Error(Throwable(message = "Internal server error"))
        }
    } catch (e: HttpException) {
        println("Error causing this ---> ${e.message ?: e.localizedMessage}",)
        NetworkResult.Error(e)
    } catch (e: Throwable) {
        println("Exception causing this ---> ${e.message ?: e.localizedMessage}",)
        NetworkResult.Exception(e)
    }
}