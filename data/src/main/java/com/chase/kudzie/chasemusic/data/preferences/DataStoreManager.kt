package com.chase.kudzie.chasemusic.data.preferences

import androidx.datastore.core.DataStore
import androidx.datastore.preferences.core.Preferences
import androidx.datastore.preferences.core.edit
import androidx.datastore.preferences.core.preferencesKey
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class DataStoreManager @Inject constructor(private val dataStore: DataStore<Preferences>) {

    companion object {
        private val QUEUE_POSITION = preferencesKey<Int>("current_queue_position")
    }

    suspend fun setCurrentQueuePosition(currentPosition: Int) {
        dataStore.edit { preferences ->
            preferences[QUEUE_POSITION] = currentPosition
        }
    }

    fun getCurrentQueuePosition(): Flow<Int> {
        return dataStore.data.map { preferences ->
            preferences[QUEUE_POSITION] ?: 0
        }
    }

}