package com.chase.kudzie.chasemusic.service.music.data

import android.support.v4.media.session.PlaybackStateCompat
import com.chase.kudzie.chasemusic.service.music.model.MediaItem
import java.util.concurrent.TimeUnit

internal sealed class Event {
    data class Metadata(
        val mediaItem: MediaItem
    ) : Event()

    data class State(
        val state: PlaybackStateCompat
    ) : Event()
}


internal data class NotificationState(
    var id: Long = -1,
    var title: String = "",
    var artist: String = "",
    var album: String = "",
    var duration: Long = -1,
    var albumId: Long = -1,
    var isPlaying: Boolean = false,
    var currentSeekPos: Long = -1
) {

    private fun isStateValid(): Boolean {
        return id != -1L &&
                title.isNotBlank() &&
                artist.isNotBlank() &&
                album.isNotBlank() &&
                duration != -1L
    }

    fun updateMetadata(mediaItem: MediaItem): Boolean {
        this.id = mediaItem.id
        this.title = mediaItem.title
        this.artist = mediaItem.artist
        this.album = mediaItem.album
        this.duration = mediaItem.duration
        this.albumId = mediaItem.albumId
        return isStateValid()
    }

    fun updateState(state: PlaybackStateCompat): Boolean {
        this.isPlaying = state.state == PlaybackStateCompat.STATE_PLAYING
        this.currentSeekPos = state.position
        return isStateValid()
    }

    fun isDifferentMetadata(mediaItem: MediaItem): Boolean {
        return this.id != mediaItem.id ||
                this.title != mediaItem.title ||
                this.artist != mediaItem.artist ||
                this.album != mediaItem.album ||
                this.duration != mediaItem.duration ||
                this.albumId != mediaItem.albumId
    }

    fun isDifferentState(state: PlaybackStateCompat): Boolean {
        val isPlaying = state.state == PlaybackStateCompat.STATE_PLAYING
        val currentSeekPos = TimeUnit.MILLISECONDS.toSeconds(state.position)
        return this.isPlaying != isPlaying ||
                TimeUnit.MILLISECONDS.toSeconds(this.currentSeekPos) != currentSeekPos
    }


    fun copy(): NotificationState {
        return NotificationState(
            id, title, artist, album, duration, albumId, isPlaying, currentSeekPos
        )
    }

}