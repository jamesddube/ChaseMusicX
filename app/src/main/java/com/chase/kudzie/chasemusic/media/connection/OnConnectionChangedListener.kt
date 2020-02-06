package com.chase.kudzie.chasemusic.media.connection

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat

interface OnConnectionChangedListener {
    fun onConnectionSuccess(
        mediaBrowser: MediaBrowserCompat,
        callback: MediaControllerCompat.Callback
    )

    fun onConnectionFailed(
        mediaBrowser: MediaBrowserCompat,
        callback: MediaControllerCompat.Callback
    )
}