package com.chase.kudzie.chasemusic.service.music.data

import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
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

    fun prepare(){
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

}
