package com.chase.kudzie.chasemusic.service.music.repository

import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.service.music.model.PlayableMediaItem

internal interface QueueRepository {

    fun sortSongs()

    fun shuffleSongs()

    fun isQueueEmpty(): Boolean

    suspend fun skipToNext(isFromUser: Boolean): PlayableMediaItem?

    suspend fun skipToPrevious(): PlayableMediaItem?

    suspend fun prepare(): PlayableMediaItem?

    suspend fun onPlayFromMediaId(mediaIdCategory: MediaIdCategory): PlayableMediaItem?

    suspend fun getCurrentPlayingSong(): PlayableMediaItem?

    suspend fun removeSongFromQueue(positionAt: Int)

    suspend fun addSongToQueue(mediaIdCategory: MediaIdCategory)

    fun swap(from: Int, to: Int)

    fun clearQueue()
}