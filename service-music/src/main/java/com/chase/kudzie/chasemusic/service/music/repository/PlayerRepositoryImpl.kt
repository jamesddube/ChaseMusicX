package com.chase.kudzie.chasemusic.service.music.repository

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.provider.MediaStore
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.annotation.CallSuper
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.chase.kudzie.chasemusic.service.music.data.MediaPlaybackState
import com.chase.kudzie.chasemusic.service.music.injection.scope.ServiceContext
import com.chase.kudzie.chasemusic.service.music.model.MediaItem
import com.google.android.exoplayer2.ExoPlaybackException
import com.google.android.exoplayer2.ExoPlayerFactory
import com.google.android.exoplayer2.Player
import com.google.android.exoplayer2.source.ConcatenatingMediaSource
import com.google.android.exoplayer2.source.ProgressiveMediaSource
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory
import com.google.android.exoplayer2.util.Util
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */

class PlayerRepositoryImpl @Inject constructor(
    @ServiceContext private val context: Context,
    private val playbackState: MediaPlaybackState,
    private val serviceController: ServiceController
) : PlayerRepository,
    Player.EventListener,
    DefaultLifecycleObserver {

    //We may have more than one listener working in different places
    private val listeners = mutableListOf<PlayerPlaybackState.Listener>()

    //Init All exoplayer stuff
    private val trackSelector = DefaultTrackSelector()
    private var player = ExoPlayerFactory.newSimpleInstance(context, trackSelector)
    private val userAgent: String = Util.getUserAgent(context, "ChaseMusic")
    private val dataSourceFactory = DefaultDataSourceFactory(context, userAgent)
    private val mediaSource = ProgressiveMediaSource.Factory(dataSourceFactory)

    override fun isPlaying(): Boolean {
        return player.isPlaying
    }

    override fun resume() {
        player.playWhenReady = true
        val playerPlaybackState =
            playbackState.update(PlaybackStateCompat.STATE_PLAYING, getCurrentSeekPos())
        listeners.forEach {
            it.onPlaybackStateChanged(playerPlaybackState)
        }
        serviceController.start()
    }

    override fun play(mediaItem: MediaItem, hasTrackEnded: Boolean) {
        val media =
            ConcatenatingMediaSource(
                mediaSource.createMediaSource(getSongUri(mediaItem.id))
            )
        playbackState.prepare()
        player.prepare(media, true, true)
        player.playWhenReady = true
        val playerPlaybackState =
            playbackState.update(PlaybackStateCompat.STATE_PLAYING, getCurrentSeekPos())
        listeners.forEach {
            it.onPlaybackStateChanged(playerPlaybackState)
            it.onMetadataChanged(mediaItem)
        }

    }

    override fun pause(isServiceAlive: Boolean) {
        player.playWhenReady = false
        val currState =
            if (isPlaying()) PlaybackStateCompat.STATE_PLAYING else PlaybackStateCompat.STATE_PAUSED
        val playerPlaybackState =
            playbackState.update(PlaybackStateCompat.STATE_PAUSED, getCurrentSeekPos())

        listeners.forEach {
            it.onPlaybackStateChanged(playerPlaybackState)
        }

        if (!isServiceAlive) {
            serviceController.stop()
        }
    }

    override fun seekTo(milliseconds: Long) {
        val currState =
            if (isPlaying()) PlaybackStateCompat.STATE_PLAYING else PlaybackStateCompat.STATE_PAUSED
        val playerPlaybackState = playbackState.update(currState, milliseconds)
        listeners.forEach {
            it.onPlaybackStateChanged(playerPlaybackState)
            it.onSeek(milliseconds)
        }
        player.seekTo(milliseconds)

        if (isPlaying()) {
            serviceController.start()
        } else {
            serviceController.stop()
        }
    }

    override fun likeTrack() {
        TODO("This supposed to make a database or playlist entry into favorites.")
    }

    override fun prepare(mediaItem: MediaItem) {
        val media =
            ConcatenatingMediaSource(
                mediaSource.createMediaSource(getSongUri(mediaItem.id))
            )
        playbackState.prepare()
        player.prepare(media)
        player.playWhenReady = false
        player.seekTo(0L)
        playbackState.updatePlaybackState(getCurrentSeekPos())
        listeners.forEach {
            it.onPrepare(mediaItem)
        }
    }

    override fun getDuration(): Long = player.duration

    private fun getCurrentSeekPos(): Long = player.currentPosition

    private fun getSongUri(id: Long): Uri {
        return ContentUris.withAppendedId(MediaStore.Audio.Media.EXTERNAL_CONTENT_URI, id)
    }

    @CallSuper
    override fun onDestroy(owner: LifecycleOwner) {
        player.release()
    }

    override fun addListener(listener: PlayerPlaybackState.Listener) {
        listeners.add(listener)
    }

    override fun removeListener(listener: PlayerPlaybackState.Listener) {
        listeners.remove(listener)
    }

    override fun onPlayerError(error: ExoPlaybackException?) {
        super.onPlayerError(error)
        when (error!!.type) {
            ExoPlaybackException.TYPE_SOURCE -> Log.e(
                "EXO",
                "TYPE_SOURCE: " + error.sourceException.message
            )
            ExoPlaybackException.TYPE_RENDERER -> Log.e(
                "EXO",
                "TYPE_RENDERER: " + error.rendererException.message
            )
            ExoPlaybackException.TYPE_UNEXPECTED -> Log.e(
                "EXO",
                "TYPE_UNEXPECTED: " + error.unexpectedException.message
            )
            ExoPlaybackException.TYPE_OUT_OF_MEMORY -> {
                Log.e(
                    "EXO",
                    "TYPE_OUT_OF_MEMORY: " + error.unexpectedException.message
                )
            }
            ExoPlaybackException.TYPE_REMOTE -> {
                Log.e(
                    "EXO",
                    "TYPE_REMOTE: " + error.unexpectedException.message
                )
            }
        }
    }
}