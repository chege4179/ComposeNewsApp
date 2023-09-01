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
package com.peterchege.composenewsapp.core.datastore

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.intPreferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

val REMOTE_KEY = intPreferencesKey("remote_key")
class DefaultRemoteKeyProvider @Inject constructor(
    private val dataStore: DataStore<Preferences>
) {

    suspend fun setRemoteKey(key: Int) {
        dataStore.edit { preferences ->
            preferences[REMOTE_KEY] = key
        }
    }
    fun getRemoteKey(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[REMOTE_KEY] ?: 1
        }
    }
}