package com.chase.kudzie.chasemusic.service.music.notification

import android.app.PendingIntent
import android.content.ComponentName
import android.content.Context
import android.support.v4.media.session.PlaybackStateCompat
import androidx.core.app.NotificationCompat
import androidx.media.session.MediaButtonReceiver
import com.kudziechase.chasemusic.service.music.R

object Actions {

    fun playPauseAction(context: Context, isPlaying: Boolean): NotificationCompat.Action {
        val resId = if (isPlaying) R.drawable.ic_play_arrow_big else R.drawable.ic_pause_big
        return NotificationCompat.Action.Builder(
            resId,
            "Toggle Play Pause",
            buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_PLAY_PAUSE)
        ).build()
    }

    fun skipToNextAction(context: Context): NotificationCompat.Action {
        val resId = R.drawable.ic_skip_next
        return NotificationCompat.Action.Builder(
            resId,
            "Skip to Next",
            buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_SKIP_TO_NEXT)
        ).build()
    }

    fun skipToPreviousAction(context: Context): NotificationCompat.Action {
        val resId = R.drawable.ic_skip_previous
        return NotificationCompat.Action.Builder(
            resId,
            "Skip to Previous",
            buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_SKIP_TO_PREVIOUS)
        ).build()
    }

    fun stopAction(context: Context): PendingIntent {
        return buildMediaButtonPendingIntent(context, PlaybackStateCompat.ACTION_STOP)
    }

    fun likeAction(context: Context) {
        TODO("implement when enabled")
    }

    private fun buildMediaButtonPendingIntent(context: Context, action: Long): PendingIntent {
        return MediaButtonReceiver.buildMediaButtonPendingIntent(
            context, ComponentName(context, MediaButtonReceiver::class.java), action
        )
    }
}