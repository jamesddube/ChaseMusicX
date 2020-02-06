package com.chase.kudzie.chasemusic.media.connection

import android.support.v4.media.MediaBrowserCompat

class MusicServiceConnection(
    private val connectionCallback: IMediaConnectionCallback
) : MediaBrowserCompat.ConnectionCallback() {
    override fun onConnected() {
        connectionCallback.onConnectionStateChanged(ConnectionState.CONNECTED)
    }

    override fun onConnectionSuspended() {
        connectionCallback.onConnectionStateChanged(ConnectionState.FAILED)
    }

    override fun onConnectionFailed() {
        connectionCallback.onConnectionStateChanged(ConnectionState.FAILED)
    }
}