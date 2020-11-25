package com.chase.kudzie.chasemusic.domain.repository

interface PreferencesRepository {

    fun setShuffleMode(shuffleMode: Int)

    fun setRepeatMode(repeatMode: Int)

    fun setCurrentQueuePosition(positionInQueue: Int)

    fun setCurrentPlayMode(playMode: Int)

    fun setCurrentSongDurationPos(currentDuration: Long)

    fun getShuffleMode(): Int

    fun getRepeatMode(): Int

    fun getCurrentQueuePosition(): Int

    fun getCurrentPlayMode(): Int

    fun getCurrentSongDurationPos(): Long

}