package com.chase.kudzie.chasemusic.service.music.repository

interface QueueRepository {

    fun sortSongs()

    fun shuffleSongs()

    fun isQueueEmpty(): Boolean

    fun skipToNext()

    fun skipToPrevious()

    fun prepare()

}