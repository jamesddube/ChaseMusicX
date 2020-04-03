package com.chase.kudzie.chasemusic.service.music.notification

import android.annotation.SuppressLint
import android.app.Notification
import android.app.NotificationManager
import android.app.PendingIntent
import android.content.ContentUris
import android.content.Context
import android.content.Intent
import android.graphics.Typeface
import android.net.Uri
import android.support.v4.media.session.MediaSessionCompat
import android.text.SpannableString
import android.text.style.StyleSpan
import androidx.core.app.NotificationCompat
import androidx.media.app.NotificationCompat.MediaStyle
import com.chase.kudzie.chasemusic.service.music.MusicService
import com.chase.kudzie.chasemusic.service.music.data.NotificationState
import com.chase.kudzie.chasemusic.service.music.extensions.retrieveGlideBitmap
import com.kudziechase.chasemusic.service.music.R
import kotlinx.coroutines.yield
import javax.inject.Inject

@SuppressLint("RestrictedApi")
internal open class MediaNotificationV21 @Inject constructor(
    val service: MusicService,
    private val mediaSession: MediaSessionCompat
) : INotificationManager {


    protected val notificationManager by lazy {
        service.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
    }

    protected var builder = NotificationCompat.Builder(service, INotificationManager.CHANNEL_ID)

    var isCreated = false

    private fun create() {
        if (isCreated) {
            return
        }

        val mediaStyle = MediaStyle()
            .setMediaSession(mediaSession.sessionToken)
            .setShowActionsInCompactView(0, 1, 2)

        builder.setSmallIcon(R.drawable.ic_music_note)
            .setVisibility(NotificationCompat.VISIBILITY_PUBLIC)
            .setContentIntent(buildContentIntent())
            .setCategory(NotificationCompat.CATEGORY_TRANSPORT)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setStyle(mediaStyle)
            .setDeleteIntent(
                Actions.stopAction(service)
            )
            .addAction(Actions.skipToPreviousAction(service))
            .addAction(Actions.playPauseAction(service, isPlaying = false))
            .addAction(Actions.skipToNextAction(service))
            .setGroup("com.chase.kudzie.CHASEMUSIC")

        initAnyAdditional()

        isCreated = true
    }

    protected open fun initAnyAdditional() {}

    private fun buildContentIntent(): PendingIntent {
        val intent = Intent(service, Class.forName("com.chase.kudzie.chasemusic.MainActivity"))
        intent.action = "ChaseMusic.action.content.view"
        return PendingIntent.getActivity(service, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    override suspend fun update(state: NotificationState): Notification {
        create()

        val title = state.title
        val artist = state.artist
        val album = state.album

        val spannableTitle = SpannableString(title)
        spannableTitle.setSpan(StyleSpan(Typeface.BOLD), 0, title.length, 0)
        updateMetadataImpl(state.id, spannableTitle, artist, album, state.albumId)
        updateState(state.isPlaying)

        yield()

        val notification = builder.build()
        notificationManager.notify(INotificationManager.NOTIFICATION_ID, notification)
        return notification
    }

    private fun updateState(isPlaying: Boolean) {
        builder.mActions[1] = Actions.playPauseAction(service, isPlaying)
        builder.setSmallIcon(if (isPlaying) R.drawable.ic_music_note else R.drawable.ic_pause_big)
        builder.setOngoing(isPlaying)
    }


    protected open suspend fun updateMetadataImpl(
        id: Long,
        title: SpannableString,
        artist: String,
        album: String,
        albumId: Long
    ) {
        builder.mActions[0] = Actions.skipToPreviousAction(service)
        builder.mActions[2] = Actions.skipToNextAction(service)

        val albumArt = service.retrieveGlideBitmap(getAlbumArtUri(albumId))

        builder.setLargeIcon(albumArt)
            .setContentTitle(title)
            .setContentText(artist)
            .setSubText(album)

    }

    private fun getAlbumArtUri(albumId: Long): Uri =
        ContentUris.withAppendedId(
            Uri.parse("content://media/external/audio/albumart"), albumId
        )


    override fun cancel() {
        notificationManager.cancel(INotificationManager.NOTIFICATION_ID)
    }

}