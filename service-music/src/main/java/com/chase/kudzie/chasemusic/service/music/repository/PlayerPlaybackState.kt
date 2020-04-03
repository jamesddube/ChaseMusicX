package com.chase.kudzie.chasemusic.service.music.repository

import android.support.v4.media.session.PlaybackStateCompat
import com.chase.kudzie.chasemusic.service.music.model.MediaItem

interface PlayerPlaybackState {
    fun addListener(listener: Listener)
    fun removeListener(listener: Listener)

    interface Listener {
        fun onPrepare(mediaItem: MediaItem) {}
        fun onMetadataChanged(mediaItem: MediaItem) {}
        fun onPlaybackStateChanged(state: PlaybackStateCompat) {}
        fun onSeek(to: Long) {}
    }
}