package com.chase.kudzie.chasemusic.injection.module

import android.view.View
import androidx.lifecycle.ViewModel
import com.chase.kudzie.chasemusic.domain.model.Album
import com.chase.kudzie.chasemusic.viewmodel.AlbumViewModel
import com.chase.kudzie.chasemusic.viewmodel.SongViewModel
import dagger.Binds
import dagger.MapKey
import dagger.Module
import dagger.multibindings.IntoMap
import kotlin.reflect.KClass

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

@MustBeDocumented
@Target(AnnotationTarget.FUNCTION)
@Retention(AnnotationRetention.RUNTIME)
@MapKey
annotation class ViewModelKey(val value: KClass<out ViewModel>)