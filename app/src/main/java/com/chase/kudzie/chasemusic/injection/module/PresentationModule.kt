package com.chase.kudzie.chasemusic.injection.module

import androidx.lifecycle.ViewModel
import com.chase.kudzie.chasemusic.injection.scope.PerFragment
import com.chase.kudzie.chasemusic.injection.scope.ViewModelKey
import com.chase.kudzie.chasemusic.viewmodel.AlbumViewModel
import com.chase.kudzie.chasemusic.viewmodel.SongViewModel
import dagger.Binds
import dagger.Module
import dagger.multibindings.IntoMap

@Module
abstract class PresentationModule {

    @Binds
    @IntoMap
    @ViewModelKey(AlbumViewModel::class)
    abstract fun bindAlbumViewModel(viewModel: AlbumViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(SongViewModel::class)
    abstract fun bindSongViewModel(viewModel: SongViewModel): ViewModel

}

