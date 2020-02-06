package com.chase.kudzie.chasemusic.media.controller

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat

internal class MediaControllerCallback(
    private val controllerCallback: IMediaControllerCallback

) : MediaControllerCompat.Callback() {

    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        controllerCallback.onMetadataChanged(metadata)
    }

    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
        controllerCallback.onPlaybackStateChanged(state)
    }

}