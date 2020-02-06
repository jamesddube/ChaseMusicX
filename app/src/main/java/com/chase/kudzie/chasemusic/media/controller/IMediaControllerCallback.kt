package com.chase.kudzie.chasemusic.media.controller

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.PlaybackStateCompat

internal interface IMediaControllerCallback {

    fun onMetadataChanged(metadata: MediaMetadataCompat?)

    fun onPlaybackStateChanged(state: PlaybackStateCompat?)

}