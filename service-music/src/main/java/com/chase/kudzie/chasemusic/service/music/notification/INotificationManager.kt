package com.chase.kudzie.chasemusic.service.music.notification

import android.app.Notification
import com.chase.kudzie.chasemusic.service.music.data.NotificationState

internal interface INotificationManager {
    companion object {
        const val NOTIFICATION_ID: Int = 0x15F70BB
        const val CHANNEL_ID: String = "com.chase.kudzie.chasemusic.NOW_PLAYING"
        const val MODE_READ_ONLY = "r"
    }

    suspend fun update(state: NotificationState): Notification
    fun cancel()
}