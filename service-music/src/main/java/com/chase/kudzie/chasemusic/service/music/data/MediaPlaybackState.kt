package com.chase.kudzie.chasemusic.service.music.data

import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import com.chase.kudzie.chasemusic.domain.scope.ApplicationContext
import javax.inject.Inject

class MediaPlaybackState @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mediaSession: MediaSessionCompat
) {

    private val playbackStateBuilder = PlaybackStateCompat.Builder().apply {
        setState(
            PlaybackStateCompat.STATE_PAUSED, 0, 0f
        )
        setActions(makeActions())
    }

    fun prepare() {
        mediaSession.setPlaybackState(playbackStateBuilder.build())
    }

    private fun makeActions(): Long {
        return PlaybackStateCompat.ACTION_PAUSE or
                PlaybackStateCompat.ACTION_PLAY or
                PlaybackStateCompat.ACTION_PLAY_FROM_URI or
                PlaybackStateCompat.ACTION_PLAY_FROM_MEDIA_ID or
                PlaybackStateCompat.ACTION_PLAY_FROM_SEARCH or
                PlaybackStateCompat.ACTION_PLAY_PAUSE or
                PlaybackStateCompat.ACTION_PREPARE or
                PlaybackStateCompat.ACTION_PREPARE_FROM_MEDIA_ID or
                PlaybackStateCompat.ACTION_PREPARE_FROM_SEARCH or
                PlaybackStateCompat.ACTION_PREPARE_FROM_URI or
                PlaybackStateCompat.ACTION_SEEK_TO or
                PlaybackStateCompat.ACTION_SET_SHUFFLE_MODE or
                PlaybackStateCompat.ACTION_SET_REPEAT_MODE or
                PlaybackStateCompat.ACTION_SKIP_TO_NEXT or
                PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS or
                PlaybackStateCompat.ACTION_SKIP_TO_QUEUE_ITEM or
                PlaybackStateCompat.ACTION_STOP
    }

    fun updatePlaybackState(currentSeekPos: Long) {
        val currentState = mediaSession.controller?.playbackState
        if (currentState == null) {
            playbackStateBuilder.setState(
                PlaybackStateCompat.STATE_PAUSED,
                currentSeekPos,
                0f
            )
        } else {
            playbackStateBuilder.setState(currentState.state, currentState.position, 1F)
        }
        mediaSession.setPlaybackState(playbackStateBuilder.build())
    }

    fun update(state: Int, currentSeekPos: Long): PlaybackStateCompat {
        val isPlaying = state == PlaybackStateCompat.STATE_PLAYING
        val playbackSpeed = 1F
        playbackStateBuilder.setState(state, currentSeekPos, (if (isPlaying) playbackSpeed else 0F))

        //TODO Maybe save the current seek pos somewhere
        val playbackState = playbackStateBuilder.build()
        mediaSession.setPlaybackState(playbackState)

        return playbackState
    }

    fun toggleSkip() {
        TODO("Implement after queue")
    }

}
