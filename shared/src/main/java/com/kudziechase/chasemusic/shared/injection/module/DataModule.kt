package com.kudziechase.chasemusic.shared.injection.module

import com.chase.kudzie.chasemusic.data.repository.AlbumRepositoryImpl
import com.chase.kudzie.chasemusic.data.repository.ArtistRepositoryImpl
import com.chase.kudzie.chasemusic.data.repository.PlaylistRepositoryImpl
import com.chase.kudzie.chasemusic.data.repository.SongRepositoryImpl
import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import com.chase.kudzie.chasemusic.domain.repository.ArtistRepository
import com.chase.kudzie.chasemusic.domain.repository.PlaylistRepository
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
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
}