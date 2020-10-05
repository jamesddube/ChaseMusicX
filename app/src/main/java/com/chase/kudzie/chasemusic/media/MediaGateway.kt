package com.chase.kudzie.chasemusic.media

import android.content.ComponentName
import android.content.Context
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.extensions.canReadStorage
import com.chase.kudzie.chasemusic.media.connection.ConnectionState
import com.chase.kudzie.chasemusic.media.connection.IMediaConnectionCallback
import com.chase.kudzie.chasemusic.media.connection.MusicServiceConnection
import com.chase.kudzie.chasemusic.media.connection.OnConnectionChangedListener
import com.chase.kudzie.chasemusic.media.controller.IMediaControllerCallback
import com.chase.kudzie.chasemusic.media.controller.MediaControllerCallback
import com.chase.kudzie.chasemusic.media.model.MediaMetadata
import com.chase.kudzie.chasemusic.media.model.MediaPlaybackState
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.ConflatedBroadcastChannel
import kotlinx.coroutines.flow.MutableStateFlow
import java.lang.IllegalStateException


class MediaGateway(
    private val context: Context,
    private val onConnectionChanged: OnConnectionChangedListener
) : CoroutineScope by MainScope(),
    IMediaControllerCallback,
    IMediaConnectionCallback {

    private val mediaBrowser: MediaBrowserCompat by lazy(LazyThreadSafetyMode.NONE) {
        MediaBrowserCompat(
            context,
            ComponentName(context, "com.chase.kudzie.chasemusic.service.music.MusicService"),
            MusicServiceConnection(this),
            null
        )
    }

    val callback: MediaControllerCompat.Callback = MediaControllerCallback(this)
    private var job: Job? = null

    private val connectionPublisher = ConflatedBroadcastChannel<ConnectionState>()

    private val metadataLiveData = MutableLiveData<MediaMetadata>()
    private val playbackStateLiveData = MutableLiveData<MediaPlaybackState>()
    private val someFlow = MutableStateFlow<Song?>(null)

    fun connect() {
        if (!canReadStorage(context)) {
            return
        }
        job?.cancel()

        job = launch {
            for (state in connectionPublisher.openSubscription()) {
                when (state) {
                    ConnectionState.CONNECTED -> {
                        onConnectionChanged.onConnectionSuccess(mediaBrowser, callback)
                    }
                    ConnectionState.FAILED -> {
                        onConnectionChanged.onConnectionFailed(mediaBrowser, callback)
                    }
                }
            }
        }

        if (!mediaBrowser.isConnected) {
            try {
                mediaBrowser.connect()
            } catch (ex: IllegalStateException) {
                Log.e("MediaGateway", ex.message!!)
            }
        }
    }

    fun disconnect() {
        job?.cancel()
        if (mediaBrowser.isConnected) {
            mediaBrowser.disconnect()
        }
    }

    fun initialize(mediaController: MediaControllerCompat) {
        callback.onMetadataChanged(mediaController.metadata)
        callback.onPlaybackStateChanged(mediaController.playbackState)
    }


    override fun onMetadataChanged(metadata: MediaMetadataCompat?) {
        if (metadata != null) {
            metadataLiveData.value = MediaMetadata(metadata)
        }
    }

    override fun onPlaybackStateChanged(state: PlaybackStateCompat?) {
        state?.let {
            playbackStateLiveData.value = MediaPlaybackState(state)
        }
    }

    override fun onConnectionStateChanged(state: ConnectionState) {
        connectionPublisher.offer(state)
    }

    fun observeMetadata(): LiveData<MediaMetadata> = metadataLiveData
    fun observePlaybackState(): LiveData<MediaPlaybackState> = playbackStateLiveData
}