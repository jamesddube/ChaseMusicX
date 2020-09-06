package com.chase.kudzie.chasemusic.service.music.injection

import android.content.ComponentName
import android.content.Context
import android.support.v4.media.session.MediaSessionCompat
import androidx.media.session.MediaButtonReceiver
import com.chase.kudzie.chasemusic.service.music.MusicService
import com.chase.kudzie.chasemusic.service.music.injection.scope.PerService
import com.chase.kudzie.chasemusic.service.music.injection.scope.ServiceContext
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
    abstract fun bindsPlayerPlaybackState(player: PlayerRepository): PlayerPlaybackState

    @Binds
    @PerService
    abstract fun bindsQueueRepository(repository: QueueRepositoryImpl): QueueRepository

    @Binds
    @PerService
    abstract fun bindsServiceController(service: MusicService): ServiceController


}