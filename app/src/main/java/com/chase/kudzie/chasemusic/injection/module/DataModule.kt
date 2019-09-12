package com.chase.kudzie.chasemusic.injection.module

import com.chase.kudzie.chasemusic.data.repository.AlbumRepositoryImpl
import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import dagger.Binds
import dagger.Module

@Module
abstract class DataModule {

    @Binds
    abstract fun bindsAlbumsRepository(albumsRepository: AlbumRepositoryImpl): AlbumRepository
}