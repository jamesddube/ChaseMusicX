package com.chase.kudzie.chasemusic.extensions

import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat


fun MediaControllerCompat.playPause() {
    val playbackState = playbackState
    playbackState?.let {
        when (it.state) {
            PlaybackStateCompat.STATE_PLAYING -> transportControls?.pause()
            PlaybackStateCompat.STATE_PAUSED -> transportControls?.play()
            else -> {
            }
        }
    }
}