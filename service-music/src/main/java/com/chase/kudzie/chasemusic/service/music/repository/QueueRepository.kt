package com.chase.kudzie.chasemusic.service.music.repository

import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.service.music.model.PlayableMediaItem

interface QueueRepository {

    fun sortSongs()

    fun shuffleSongs()

    fun isQueueEmpty(): Boolean

    suspend fun skipToNext(): PlayableMediaItem?

    suspend fun skipToPrevious(): PlayableMediaItem?

    fun prepare()

    suspend fun onPlayFromMediaId(mediaIdCategory: MediaIdCategory): PlayableMediaItem?

    suspend fun getCurrentPlayingSong(): PlayableMediaItem?

    //TODO choose songID or maybe Index
    fun removeSongFromQueue()

    fun addSongToQueue()

}