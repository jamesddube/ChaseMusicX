package com.chase.kudzie.chasemusic.media.model

import android.support.v4.media.session.PlaybackStateCompat

data class MediaShuffleMode(var shuffleMode: Int = PlaybackStateCompat.SHUFFLE_MODE_NONE) {
    enum class Mode { NONE, ALL }

    fun getMode(): MediaShuffleMode.Mode {
        return when (shuffleMode) {
            PlaybackStateCompat.SHUFFLE_MODE_NONE -> Mode.NONE
            PlaybackStateCompat.SHUFFLE_MODE_ALL -> Mode.ALL
            else -> Mode.NONE
        }
    }

}