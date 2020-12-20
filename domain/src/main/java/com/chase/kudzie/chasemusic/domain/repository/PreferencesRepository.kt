package com.chase.kudzie.chasemusic.domain.repository

import kotlinx.coroutines.flow.Flow

interface PreferencesRepository {

    fun setShuffleMode(shuffleMode: Int)

    fun setRepeatMode(repeatMode: Int)

    suspend fun setCurrentQueuePosition(positionInQueue: Int)

    fun setCurrentPlayMode(playMode: Int)

    fun setCurrentSongDurationPos(currentDuration: Long)

    fun getShuffleMode(): Int

    fun getRepeatMode(): Int

    fun observeCurrentQueuePosition(): Flow<Int>

    fun getCurrentQueuePosition(): Int

    fun getCurrentPlayMode(): Int

    fun getCurrentSongDurationPos(): Long

}