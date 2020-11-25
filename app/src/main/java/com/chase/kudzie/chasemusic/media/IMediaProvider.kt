package com.chase.kudzie.chasemusic.media

import androidx.lifecycle.LiveData
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.media.model.*
import kotlinx.coroutines.flow.Flow

interface IMediaProvider {
    fun playMediaFromId(mediaIdCategory: MediaIdCategory)
    fun playPause()
    fun prepare()
    fun stop()
    fun seekTo(pos: Long)
    fun skipToNext()
    fun skipToPrevious()
    fun skipToQueueItem(id: Long)
    fun favouriteSong(songId: Long)
    fun toggleShuffleMode()
    fun toggleRepeatMode()

    fun observeMetadata(): LiveData<MediaMetadata>
    fun observePlaybackState(): LiveData<MediaPlaybackState>
    fun observeRepeatMode(): LiveData<MediaRepeatMode>
    fun observeShuffleMode(): LiveData<MediaShuffleMode>

    fun observePlayingQueue(): Flow<List<PlayableMediaItem>>
}