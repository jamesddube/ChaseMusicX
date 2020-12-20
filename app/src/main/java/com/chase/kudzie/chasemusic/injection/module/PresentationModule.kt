package com.chase.kudzie.chasemusic.injection.module

import androidx.lifecycle.ViewModel
import com.chase.kudzie.chasemusic.injection.scope.ViewModelKey
import com.chase.kudzie.chasemusic.ui.albums.AlbumViewModel
import com.chase.kudzie.chasemusic.ui.albumdetails.AlbumDetailsViewModel
import com.chase.kudzie.chasemusic.ui.artistdetails.ArtistDetailsViewModel
import com.chase.kudzie.chasemusic.ui.artists.ArtistsViewModel
import com.chase.kudzie.chasemusic.ui.playlistdetails.PlaylistDetailsViewModel
import com.chase.kudzie.chasemusic.ui.playlists.PlaylistsViewModel
import com.chase.kudzie.chasemusic.ui.queue.QueueViewModel
import com.chase.kudzie.chasemusic.ui.songs.SongViewModel
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

    @Binds
    @IntoMap
    @ViewModelKey(ArtistsViewModel::class)
    abstract fun bindArtistViewModel(viewModel: ArtistsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlaylistsViewModel::class)
    abstract fun bindPlaylistViewModel(viewModel: PlaylistsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(AlbumDetailsViewModel::class)
    abstract fun bindAlbumSongsViewModel(viewModel: AlbumDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(ArtistDetailsViewModel::class)
    abstract fun bindArtistDetailsViewModel(viewModel: ArtistDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(PlaylistDetailsViewModel::class)
    abstract fun bindPlaylistDetailsViewModel(viewModel: PlaylistDetailsViewModel): ViewModel

    @Binds
    @IntoMap
    @ViewModelKey(QueueViewModel::class)
    abstract fun bindQueueViewModel(viewModel: QueueViewModel): ViewModel
}

