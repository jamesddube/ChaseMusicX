package com.chase.kudzie.chasemusic.service.music

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ServiceLifecycleDispatcher
import androidx.media.MediaBrowserServiceCompat
import com.chase.kudzie.chasemusic.service.music.data.MediaMetadata
import com.chase.kudzie.chasemusic.service.music.injection.inject
import com.chase.kudzie.chasemusic.service.music.notification.MediaNotificationManager
import com.chase.kudzie.chasemusic.service.music.repository.ServiceController
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class MusicService : MediaBrowserServiceCompat(), LifecycleOwner,
    ServiceController {

    private var isServiceStarted = false
    private val dispatcher = ServiceLifecycleDispatcher(this)

    @Inject
    internal lateinit var callback: MediaSessionCallback

    @Inject
    internal lateinit var mediaSession: MediaSessionCompat

    @Inject
    internal lateinit var mediaNotification: MediaNotificationManager

    @Inject
    internal lateinit var metadata: MediaMetadata


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isServiceStarted = true

        return Service.START_NOT_STICKY
    }


    override fun onCreate() {
        super.onCreate()
        inject()
        setupMediaSession()
        lifecycle.run {
            addObserver(metadata)
            addObserver(mediaNotification)
        }
    }

    private fun setupMediaSession() {
        //Setup Media Session and retrieve a session token
        mediaSession.setMediaButtonReceiver(makeMediaButtonReceiver())
        mediaSession.setCallback(callback)
        mediaSession.isActive = true

        sessionToken = mediaSession.sessionToken
    }

    override fun onDestroy() {
        dispatcher.onServicePreSuperOnDestroy()

        super.onDestroy()
        mediaSession.setMediaButtonReceiver(null)
        mediaSession.setCallback(null)
        mediaSession.isActive = false
        mediaSession.release()


    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        result.sendResult(null)
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        return BrowserRoot(packageName, null)
    }


    private fun makeMediaButtonReceiver(): PendingIntent {
        val intent = Intent(Intent.ACTION_MEDIA_BUTTON)
        intent.setClass(this, this.javaClass)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return PendingIntent.getForegroundService(
                this,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
        }
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    override fun getLifecycle(): Lifecycle {
        return dispatcher.lifecycle
    }

    override fun start() {
        if (!isServiceStarted) {
            val intent = Intent(this, MusicService::class.java)
            intent.action = "action.KEEP_SERVICE_ALIVE"
            ContextCompat.startForegroundService(this, intent)
            isServiceStarted = true
        }
    }

    override fun stop() {
        if (isServiceStarted) {
            isServiceStarted = false
            stopSelf()
        }
    }


}

