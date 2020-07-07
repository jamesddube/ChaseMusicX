package com.chase.kudzie.chasemusic.data.repository

import android.content.SharedPreferences
import com.chase.kudzie.chasemusic.data.preferences.SharedPreferencesFactory.shuffleMode
import com.chase.kudzie.chasemusic.data.preferences.SharedPreferencesFactory.repeatMode
import com.chase.kudzie.chasemusic.data.preferences.SharedPreferencesFactory.currentPositionInQueue
import com.chase.kudzie.chasemusic.data.preferences.SharedPreferencesFactory.playMode
import com.chase.kudzie.chasemusic.data.preferences.SharedPreferencesFactory.currentSongDurationPos
import com.chase.kudzie.chasemusic.domain.repository.PreferencesRepository
import javax.inject.Inject

class PreferencesRepositoryImpl @Inject constructor(
    private val preferences: SharedPreferences
) : PreferencesRepository {

    override suspend fun setShuffleMode(shuffleMode: Int) {
        preferences.shuffleMode = shuffleMode
    }

    override suspend fun setRepeatMode(repeatMode: Int) {
        preferences.repeatMode = repeatMode
    }

    override suspend fun setCurrentQueuePosition(positionInQueue: Int) {
        preferences.currentPositionInQueue = positionInQueue
    }

    override suspend fun setCurrentPlayMode(playMode: Int) {
        preferences.playMode = playMode
    }

    override suspend fun setCurrentSongDurationPos(currentDuration: Long) {
        preferences.currentSongDurationPos = currentDuration
    }

    override suspend fun getShuffleMode(): Int {
        return preferences.shuffleMode
    }

    override suspend fun getRepeatMode(): Int {
        return preferences.repeatMode
    }

    override suspend fun getCurrentQueuePosition(): Int {
        return preferences.currentPositionInQueue
    }

    override suspend fun getCurrentPlayMode(): Int {
        return preferences.playMode
    }

    override suspend fun getCurrentSongDurationPos(): Long {
        return preferences.currentSongDurationPos
    }
}