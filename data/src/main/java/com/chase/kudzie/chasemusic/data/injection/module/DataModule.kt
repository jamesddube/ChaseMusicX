package com.chase.kudzie.chasemusic.data.injection.module

import com.chase.kudzie.chasemusic.data.repository.*
import com.chase.kudzie.chasemusic.domain.repository.*
import dagger.Binds
import dagger.Module
import javax.inject.Singleton

@Module
abstract class DataModule {

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
}