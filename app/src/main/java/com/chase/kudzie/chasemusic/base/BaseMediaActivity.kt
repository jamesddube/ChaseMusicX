package com.chase.kudzie.chasemusic.base

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.os.bundleOf
import androidx.lifecycle.LiveData
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.extensions.playPause
import com.chase.kudzie.chasemusic.media.IMediaProvider
import com.chase.kudzie.chasemusic.media.MediaGateway
import com.chase.kudzie.chasemusic.media.connection.OnConnectionChangedListener
import com.chase.kudzie.chasemusic.media.model.*
import com.chase.kudzie.chasemusic.shared.constants.CustomActions
import com.chase.kudzie.chasemusic.shared.constants.IntentArguments
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow

abstract class BaseMediaActivity : BaseActivity(),
    IMediaProvider, OnConnectionChangedListener,
    CoroutineScope by MainScope() {

    private val mediaGateway by lazy(LazyThreadSafetyMode.NONE) {
        MediaGateway(
            this,
            this
        )
    }

    override fun onStart() {
        super.onStart()
        connect()
    }

    override fun onStop() {
        super.onStop()
        mediaGateway.disconnect()
        unregisterMediaController()
    }

    private fun connect() {
        mediaGateway.connect()
    }

    override fun playMediaFromId(mediaIdCategory: MediaIdCategory) {
        transportControls()?.playFromMediaId(mediaIdCategory.toString(), null)
    }

    override fun playPause() {
        mediaController()?.playPause()
    }

    override fun onConnectionSuccess(
        mediaBrowser: MediaBrowserCompat,
        callback: MediaControllerCompat.Callback
    ) {
        try {
            registerMediaController(mediaBrowser.sessionToken, callback)
            mediaGateway.initialize(MediaControllerCompat.getMediaController(this))
        } catch (ex: Throwable) {
            ex.printStackTrace()
            onConnectionFailed(mediaBrowser, callback)
        }
    }

    override fun onConnectionFailed(
        mediaBrowser: MediaBrowserCompat,
        callback: MediaControllerCompat.Callback
    ) {
        unregisterMediaController()
    }

    private fun unregisterMediaController() {
        val mediaController = MediaControllerCompat.getMediaController(this)
        if (mediaController != null) {
            mediaController.unregisterCallback(mediaGateway.callback)
            MediaControllerCompat.setMediaController(this, null)
        }
    }

    private fun registerMediaController(
        token: MediaSessionCompat.Token,
        callback: MediaControllerCompat.Callback
    ) {
        val mediaController = MediaControllerCompat(this, token)
        mediaController.registerCallback(callback)
        MediaControllerCompat.setMediaController(this, mediaController)
    }

    private fun mediaController(): MediaControllerCompat? {
        return MediaControllerCompat.getMediaController(this)
    }

    private fun transportControls(): MediaControllerCompat.TransportControls? {
        return mediaController()?.transportControls
    }

    override fun prepare() {
        transportControls()?.prepare()
    }

    override fun stop() {
        transportControls()?.stop()
    }

    override fun seekTo(pos: Long) {
        transportControls()?.seekTo(pos)
    }

    override fun skipToNext() {
        transportControls()?.skipToNext()
    }

    override fun skipToPrevious() {
        transportControls()?.skipToPrevious()
    }

    override fun skipToQueueItem(id: Long) {
        transportControls()?.skipToQueueItem(id)
    }

    override fun favouriteSong(songId: Long) {
        TODO("Needs a custom action to be set in Transport controls and handled")
    }

    override fun toggleShuffleMode() {
        transportControls()?.setShuffleMode(PlaybackStateCompat.SHUFFLE_MODE_INVALID)
    }

    override fun toggleRepeatMode() {
        transportControls()?.setRepeatMode(PlaybackStateCompat.REPEAT_MODE_INVALID)
    }

    override fun observeMetadata(): LiveData<MediaMetadata> {
        return mediaGateway.observeMetadata()
    }

    override fun observePlaybackState(): LiveData<MediaPlaybackState> {
        return mediaGateway.observePlaybackState()
    }

    override fun observeRepeatMode(): LiveData<MediaRepeatMode> {
        return mediaGateway.observeRepeatMode()
    }

    override fun observeShuffleMode(): LiveData<MediaShuffleMode> {
        return mediaGateway.observeShuffleMode()
    }

    override fun observePlayingQueue(): Flow<List<PlayableMediaItem>> {
        return mediaGateway.observePlayingQueue()
    }

    override fun swap(from: Int, to: Int) {
        val bundleExtras = bundleOf(
            IntentArguments.INTENT_POSITION_FROM to from,
            IntentArguments.INTENT_POSITION_TO to to
        )
        transportControls()?.sendCustomAction(CustomActions.SWAP, bundleExtras)
    }

    override fun addToQueue(mediaIdCategory: MediaIdCategory) {
        TODO("Not yet implemented")
    }

    override fun removeFromQueue(positionAt: Int) {
        TODO("Not yet implemented")
    }
}