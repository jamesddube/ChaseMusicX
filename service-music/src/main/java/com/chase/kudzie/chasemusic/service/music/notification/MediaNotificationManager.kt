package com.chase.kudzie.chasemusic.service.music.notification

import android.app.Notification
import android.support.v4.media.session.PlaybackStateCompat
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.chase.kudzie.chasemusic.service.music.MusicService
import com.chase.kudzie.chasemusic.service.music.data.Event
import com.chase.kudzie.chasemusic.service.music.data.NotificationState
import com.chase.kudzie.chasemusic.service.music.extensions.isOreo
import com.chase.kudzie.chasemusic.service.music.injection.scope.PerService
import com.chase.kudzie.chasemusic.domain.model.MediaItem
import com.chase.kudzie.chasemusic.service.music.repository.PlayerPlaybackState
import com.chase.kudzie.chasemusic.shared.injection.coroutinescope.DefaultScope
import kotlinx.coroutines.*
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.filter
import javax.inject.Inject

@PerService
internal class MediaNotificationManager @Inject constructor(
    private val context: MusicService,
    private val mediaNotification: INotificationManager,
    playerPlaybackState: PlayerPlaybackState
) : CoroutineScope by DefaultScope(),
    DefaultLifecycleObserver {

    companion object {
        private const val METADATA_PUBLISH_DELAY = 350L
        private const val STATE_PUBLISH_DELAY = 100L
    }

    private var isForeground: Boolean = false

    private val playbackFlow =
        MutableSharedFlow<Event>(replay = 0, extraBufferCapacity = Channel.UNLIMITED)
    private val currState = NotificationState()
    private var job: Job? = null

    private val playerListener = object : PlayerPlaybackState.Listener {
        override fun onPrepare(mediaItem: MediaItem) {
            onNextMetadata(mediaItem)
        }

        override fun onMetadataChanged(mediaItem: MediaItem) {
            onNextMetadata(mediaItem)
        }

        override fun onPlaybackStateChanged(state: PlaybackStateCompat) {
            onNextState(state)
        }
    }

    init {
        playerPlaybackState.addListener(playerListener)

        launch {
            playbackFlow
                .filter { event ->
                    when (event) {
                        is Event.Metadata -> currState.isDifferentMetadata(event.mediaItem)
                        is Event.State -> currState.isDifferentState(event.state)
                    }
                }.collect { consumeEvent(it) }
        }
    }

    private suspend fun consumeEvent(event: Event) {
        job?.cancel()
        when (event) {
            is Event.Metadata -> {
                if (currState.updateMetadata(event.mediaItem)) {
                    publishNotification(currState.copy(), METADATA_PUBLISH_DELAY)
                }
            }
            is Event.State -> {
                if (currState.updateState(event.state)) {
                    publishNotification(currState.copy(), STATE_PUBLISH_DELAY)
                }
            }
        }
    }

    private suspend fun publishNotification(state: NotificationState, delay: Long) {
        require(currState !== state)

        if (!isForeground && isOreo()) {
            issueNotification(state)
        } else {
            job = GlobalScope.launch {
                delay(delay)
                issueNotification(state)
            }
        }
    }

    private suspend fun issueNotification(state: NotificationState) {
        val notification = mediaNotification.update(state)
        if (state.isPlaying) {
            startForeground(notification)
        } else {
            pauseForeground()
        }
    }

    override fun onDestroy(owner: LifecycleOwner) {
        stopForeground()
        job?.cancel()
        cancel()
    }

    private fun stopForeground() {
        Log.e("SERVICE", "SERVICE STOPPED")
        context.stopForeground(true)
        mediaNotification.cancel()
        isForeground = false
    }

    private fun pauseForeground() {
        context.stopForeground(false)
        isForeground = false
    }

    private fun startForeground(notification: Notification) {
        context.startForeground(INotificationManager.NOTIFICATION_ID, notification)
        isForeground = true
    }


    private fun onNextMetadata(mediaItem: MediaItem) {
        launch {
            playbackFlow.emit(Event.Metadata(mediaItem))
        }

    }

    private fun onNextState(playbackState: PlaybackStateCompat) {
        launch {
            playbackFlow.emit(Event.State(playbackState))
        }
    }
}