package com.chase.kudzie.chasemusic.injection.module

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

@Module
abstract class DataModule {

    @Binds
    abstract fun bindsAlbumsRepository(albumsRepository: AlbumRepositoryImpl): AlbumRepository

    @Binds
    abstract fun bindsArtistsRepository(artistRepository: ArtistRepositoryImpl): ArtistRepository

    @Binds
    abstract fun bindsSongRepository(songRepository: SongRepositoryImpl): SongRepository

    @Binds
    abstract fun bindsPlaylistRepository(playlistRepository: PlaylistRepositoryImpl): PlaylistRepository
}