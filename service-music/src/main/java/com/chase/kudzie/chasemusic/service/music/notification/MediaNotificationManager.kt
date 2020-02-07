package com.chase.kudzie.chasemusic.service.music.notification

import android.app.Notification
import android.app.NotificationChannel
import android.app.NotificationManager
import android.content.Context
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.net.Uri
import android.os.Build
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.media.app.NotificationCompat.MediaStyle
import com.chase.kudzie.chasemusic.service.music.MusicService
import com.chase.kudzie.chasemusic.service.music.data.MediaMetadataListener
import com.chase.kudzie.chasemusic.service.music.data.NotificationListener
import com.chase.kudzie.chasemusic.service.music.extensions.isPlaying
import com.chase.kudzie.chasemusic.service.music.extensions.isSkipToNextEnabled
import com.chase.kudzie.chasemusic.service.music.extensions.isSkipToPreviousEnabled
import com.chase.kudzie.chasemusic.service.music.injection.scope.PerService
import com.chase.kudzie.chasemusic.service.music.model.MediaItem
import com.chase.kudzie.chasemusic.service.music.repository.ServiceController
import com.chase.kudzie.chasemusic.shared.injection.coroutinescope.DefaultScope
import com.kudziechase.chasemusic.service.music.R
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

@PerService
class MediaNotificationManager @Inject constructor(
    private val context: MusicService,
    private val controller: ServiceController,
    private val mediaSession: MediaSessionCompat
) : CoroutineScope by DefaultScope(),
    NotificationListener,
    DefaultLifecycleObserver {

    private val platformNotificationManager: NotificationManager =
        context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager

    companion object {
        const val NOTIFICATION_ID: Int = 0x15F70BB
        const val CHANNEL_ID: String = "com.chase.kudzie.chasemusic.NOW_PLAYING"
        const val MODE_READ_ONLY = "r"
    }

    private suspend fun buildNotification(): Notification {
        if (shouldCreateNowPlayingChannel()) {
            createNowPlayingChannel()
        }

        val controller = MediaControllerCompat(context, mediaSession.sessionToken)
        val description = controller.metadata.description

        val playbackState = controller.playbackState

        val builder = NotificationCompat.Builder(context, CHANNEL_ID)

        var playPauseIndex = 0
        if (playbackState.isSkipToPreviousEnabled) {
            builder.addAction(Actions.skipToPreviousAction(context))
            ++playPauseIndex
        }

        builder.addAction(Actions.playPauseAction(context, playbackState.isPlaying))

        if (playbackState.isSkipToNextEnabled) {
            builder.addAction(Actions.skipToNextAction(context))
        }

        val mediaStyle = MediaStyle()
            .setCancelButtonIntent(Actions.stopAction(context))
            .setMediaSession(mediaSession.sessionToken)
            .setShowActionsInCompactView(playPauseIndex)
            .setShowCancelButton(true)

        val largeIconBitmap = description.iconUri?.let {
            resolveUriAsBitmap(it)
        }

        return builder.setContentIntent(controller.sessionActivity)
            .setContentText(description.subtitle)
            .setContentTitle(description.title)
            .setDeleteIntent(Actions.stopAction(context))
            .setLargeIcon(largeIconBitmap)
            .setOnlyAlertOnce(true)
            .setSmallIcon(R.drawable.ic_skip_next)
            .setStyle(mediaStyle)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .build()
    }

    private suspend fun resolveUriAsBitmap(uri: Uri): Bitmap? {
        return withContext(Dispatchers.IO) {
            val parcelFileDescriptor =
                context.contentResolver.openFileDescriptor(uri, MODE_READ_ONLY)
                    ?: return@withContext null
            val fileDescriptor = parcelFileDescriptor.fileDescriptor
            BitmapFactory.decodeFileDescriptor(fileDescriptor).apply {
                parcelFileDescriptor.close()
            }
        }
    }


    private fun shouldCreateNowPlayingChannel() =
        Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && !nowPlayingChannelExists()

    @RequiresApi(Build.VERSION_CODES.O)
    private fun nowPlayingChannelExists() =
        platformNotificationManager.getNotificationChannel(CHANNEL_ID) != null

    @RequiresApi(Build.VERSION_CODES.O)
    private fun createNowPlayingChannel() {
        val notificationChannel = NotificationChannel(
            CHANNEL_ID,
            "ChaseMusicX",
            NotificationManager.IMPORTANCE_LOW
        )
            .apply {
                description = "Chase Music"
            }

        platformNotificationManager.createNotificationChannel(notificationChannel)
    }

    override fun notifyReady() {
        Log.e("LAUNCH", "NOTIFICATION")
        launch {
            val notification = buildNotification()
            controller.start()
            platformNotificationManager.notify(NOTIFICATION_ID, notification)
        }
    }


}