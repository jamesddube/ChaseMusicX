package com.chase.kudzie.chasemusic.service.music.notification

import android.annotation.SuppressLint
import android.app.NotificationChannel
import android.app.NotificationManager
import android.os.Build
import android.support.v4.media.session.MediaSessionCompat
import androidx.annotation.RequiresApi
import androidx.core.app.NotificationCompat
import com.chase.kudzie.chasemusic.service.music.MusicService
import com.kudziechase.chasemusic.service.music.R
import javax.inject.Inject


@SuppressLint("RestrictedApi")
@RequiresApi(Build.VERSION_CODES.O)
internal open class MediaNotificationV26 @Inject constructor(
    service: MusicService,
    mediaSession: MediaSessionCompat
) : MediaNotificationV24(service, mediaSession) {

    override fun initAnyAdditional() {
        builder.setColorized(true)

        val nowPlayingChannelExists =
            notificationManager.getNotificationChannel(INotificationManager.CHANNEL_ID) != null

        if (!nowPlayingChannelExists) {
            createNotificationChannel()
        }
    }

    private fun createNotificationChannel() {
        val name = service.getString(R.string.channel_id_notification)
        val description = service.getString(R.string.channel_id_notification_description)

        val importance = NotificationManager.IMPORTANCE_LOW
        val channel = NotificationChannel(INotificationManager.CHANNEL_ID, name, importance)
        channel.description = description
        channel.setShowBadge(false)
        channel.lockscreenVisibility = NotificationCompat.VISIBILITY_PUBLIC
        notificationManager.createNotificationChannel(channel)
    }

}