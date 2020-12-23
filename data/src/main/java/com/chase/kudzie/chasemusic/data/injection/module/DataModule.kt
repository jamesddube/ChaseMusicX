package com.chase.kudzie.chasemusic.data.injection.module

import android.content.Context
import com.chase.kudzie.chasemusic.data.BuildConfig
import com.chase.kudzie.chasemusic.data.remote.DeezerService
import com.chase.kudzie.chasemusic.data.remote.LastFMService
import com.chase.kudzie.chasemusic.data.remote.RetrofitServiceFactory
import com.chase.kudzie.chasemusic.data.repository.*
import com.chase.kudzie.chasemusic.domain.repository.*
import com.chase.kudzie.chasemusic.domain.scope.ApplicationContext
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DataModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        fun providesLastFMService(): LastFMService {
            return RetrofitServiceFactory.makeLastFMService(BuildConfig.DEBUG)
        }

        @Provides
        @JvmStatic
        fun providesDeezerService(@ApplicationContext context: Context): DeezerService {
            return RetrofitServiceFactory.makeDeezerService(
                context,
                BuildConfig.DEBUG
            )
        }
    }

    @Binds
    @Singleton
    abstract fun bindsAlbumsRepository(albumsRepository: AlbumRepositoryImpl): AlbumRepository

    @Binds
    @Singleton
    abstract fun bindsArtistsRepository(artistRepository: ArtistRepositoryImpl): ArtistRepository

    @Binds
    @Singleton
    abstract fun bindsSongRepository(songRepository: SongRepositoryImpl): SongRepository

    @Binds
    @Singleton
    abstract fun bindsPlaylistRepository(playlistRepository: PlaylistRepositoryImpl): PlaylistRepository

    @Binds
    @Singleton
    abstract fun bindsQueueRepository(queueRepository: SongQueueRepositoryImpl): SongQueueRepository

    @Binds
    @Singleton
    abstract fun bindsLastFMRepository(lastFMRepository: LastFMRepositoryImpl): LastFMRepository

    @Binds
    @Singleton
    abstract fun bindsDeezerRepository(deezerRepository: DeezerRepositoryImpl): DeezerRepository
}