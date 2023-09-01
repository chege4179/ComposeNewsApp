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