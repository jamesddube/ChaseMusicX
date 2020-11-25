package com.chase.kudzie.chasemusic.service.music.repository

import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.service.music.model.PlayableMediaItem

internal interface QueueRepository {

    fun sortSongs()

    fun shuffleSongs()

    fun isQueueEmpty(): Boolean

    fun onSetRepeatMode()

    suspend fun skipToNext(): PlayableMediaItem?

    suspend fun skipToPrevious(): PlayableMediaItem?

    suspend fun prepare(): PlayableMediaItem?

    suspend fun onPlayFromMediaId(mediaIdCategory: MediaIdCategory): PlayableMediaItem?

    suspend fun getCurrentPlayingSong(): PlayableMediaItem?

    //TODO choose songID or maybe Index
    fun removeSongFromQueue()

    fun addSongToQueue()

}