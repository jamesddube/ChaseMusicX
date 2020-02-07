package com.chase.kudzie.chasemusic.service.music.injection

import android.content.ComponentName
import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.session.MediaButtonReceiver
import com.chase.kudzie.chasemusic.service.music.MusicService
import com.chase.kudzie.chasemusic.service.music.data.MediaMetadata
import com.chase.kudzie.chasemusic.service.music.data.MediaMetadataListener
import com.chase.kudzie.chasemusic.service.music.data.NotificationListener
import com.chase.kudzie.chasemusic.service.music.injection.scope.PerService
import com.chase.kudzie.chasemusic.service.music.injection.scope.ServiceContext
import com.chase.kudzie.chasemusic.service.music.notification.MediaNotificationManager
import com.chase.kudzie.chasemusic.service.music.repository.*
import dagger.Binds
import dagger.Module
import dagger.Provides

@Module
abstract class MusicServiceModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        @PerService
        fun providesMediaSessionCompat(service: MusicService): MediaSessionCompat {
            return MediaSessionCompat(
                service, "ChaseMusic",
                ComponentName(service, MediaButtonReceiver::class.java),
                null
            )
        }
    }

    @Binds
    @ServiceContext
    abstract fun providesContext(service: MusicService): Context

    @Binds
    @PerService
    abstract fun bindsPlayerRepository(repository: PlayerRepositoryImpl): PlayerRepository

    @Binds
    @PerService
    abstract fun bindsMediaMetadata(metadata: MediaMetadata): MediaMetadataListener

    @Binds
    @PerService
    abstract fun bindsNotification(notif: MediaNotificationManager): NotificationListener // Rough implementation ??

    @Binds
    @PerService
    abstract fun bindsQueueReepository(repository: QueueRepositoryImpl): QueueRepository

    @Binds
    @PerService
    abstract fun bindsServiceController(service: MusicService): ServiceController


}