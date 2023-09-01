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