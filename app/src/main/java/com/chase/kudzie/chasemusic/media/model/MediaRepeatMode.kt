package com.chase.kudzie.chasemusic.media.model

import android.support.v4.media.session.PlaybackStateCompat

data class MediaRepeatMode(var repeatMode: Int) {
    enum class Mode { NONE, ONE, ALL }

    fun getMode(): Mode {
        return when (repeatMode) {
            PlaybackStateCompat.REPEAT_MODE_NONE -> Mode.NONE
            PlaybackStateCompat.REPEAT_MODE_ONE -> Mode.ONE
            PlaybackStateCompat.REPEAT_MODE_ALL -> Mode.ALL
            else -> Mode.NONE
        }
    }
}