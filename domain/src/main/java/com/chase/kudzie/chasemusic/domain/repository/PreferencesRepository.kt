package com.chase.kudzie.chasemusic.domain.repository

interface PreferencesRepository {

    suspend fun setShuffleMode(shuffleMode: Int)

    suspend fun setRepeatMode(repeatMode: Int)

    suspend fun setCurrentQueuePosition(positionInQueue: Int)

    suspend fun setCurrentPlayMode(playMode: Int)

    suspend fun setCurrentSongDurationPos(currentDuration: Long)

    suspend fun getShuffleMode(): Int

    suspend fun getRepeatMode(): Int

    suspend fun getCurrentQueuePosition(): Int

    suspend fun getCurrentPlayMode(): Int

    suspend fun getCurrentSongDurationPos(): Long

}