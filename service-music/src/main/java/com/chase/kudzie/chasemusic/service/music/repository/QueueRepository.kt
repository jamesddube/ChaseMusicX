package com.chase.kudzie.chasemusic.service.music.repository

import com.chase.kudzie.chasemusic.service.music.model.MediaItem

interface QueueRepository {

    fun sortSongs()

    fun shuffleSongs()

    fun isQueueEmpty(): Boolean

    suspend fun skipToNext(): MediaItem?

    suspend fun skipToPrevious(): MediaItem?

    fun prepare()

    suspend fun onPlayFromMediaId(mediaId: String): MediaItem?

    suspend fun getCurrentPlayingSong(): MediaItem?

    //TODO choose songID or maybe Index
    fun removeSongFromQueue()

    fun addSongToQueue()

}