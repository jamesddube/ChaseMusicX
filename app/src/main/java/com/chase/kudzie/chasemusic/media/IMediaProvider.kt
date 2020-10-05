package com.chase.kudzie.chasemusic.media

import androidx.lifecycle.LiveData
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.media.model.MediaMetadata
import com.chase.kudzie.chasemusic.media.model.MediaPlaybackState
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

    fun observeMetadata(): LiveData<MediaMetadata>
    fun observePlaybackState():LiveData<MediaPlaybackState>
}