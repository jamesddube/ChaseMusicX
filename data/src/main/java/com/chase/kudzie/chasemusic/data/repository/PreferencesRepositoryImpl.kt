package com.chase.kudzie.chasemusic.data.repository

import android.content.SharedPreferences
import com.chase.kudzie.chasemusic.data.preferences.DataStoreManager
import com.chase.kudzie.chasemusic.data.preferences.SharedPreferencesFactory.currentPositionInQueue
import com.chase.kudzie.chasemusic.data.preferences.SharedPreferencesFactory.currentSongDurationPos
import com.chase.kudzie.chasemusic.data.preferences.SharedPreferencesFactory.playMode
import com.chase.kudzie.chasemusic.data.preferences.SharedPreferencesFactory.repeatMode
import com.chase.kudzie.chasemusic.data.preferences.SharedPreferencesFactory.shuffleMode
import com.chase.kudzie.chasemusic.domain.repository.PreferencesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val preferences: SharedPreferences,
    private val dataStore: DataStoreManager
) : PreferencesRepository {

    override fun setShuffleMode(shuffleMode: Int) {
        preferences.shuffleMode = shuffleMode
    }

    override fun setRepeatMode(repeatMode: Int) {
        preferences.repeatMode = repeatMode
    }

    override suspend fun setCurrentQueuePosition(positionInQueue: Int) {
        preferences.currentPositionInQueue = positionInQueue
        dataStore.setCurrentQueuePosition(positionInQueue)
    }

    override fun setCurrentPlayMode(playMode: Int) {
        preferences.playMode = playMode
    }

    override fun setCurrentSongDurationPos(currentDuration: Long) {
        preferences.currentSongDurationPos = currentDuration
    }

    override fun getShuffleMode(): Int {
        return preferences.shuffleMode
    }

    override fun getRepeatMode(): Int {
        return preferences.repeatMode
    }

    override fun observeCurrentQueuePosition(): Flow<Int> {
        return dataStore.getCurrentQueuePosition()
    }

    override fun getCurrentQueuePosition(): Int {
        return preferences.currentPositionInQueue
    }

    override fun getCurrentPlayMode(): Int {
        return preferences.playMode
    }

    override fun getCurrentSongDurationPos(): Long {
        return preferences.currentSongDurationPos
    }
}