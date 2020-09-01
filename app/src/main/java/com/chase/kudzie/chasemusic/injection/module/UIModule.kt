package com.chase.kudzie.chasemusic.injection.module

import com.chase.kudzie.chasemusic.MainActivity
import com.chase.kudzie.chasemusic.ui.albums.AlbumsFragment
import com.chase.kudzie.chasemusic.ui.albumsongs.AlbumSongsFragment
import com.chase.kudzie.chasemusic.ui.artistdetails.ArtistDetailsFragment
import com.chase.kudzie.chasemusic.ui.artists.ArtistsFragment
import com.chase.kudzie.chasemusic.ui.nowplaying.NowPlayingFragment
import com.chase.kudzie.chasemusic.ui.playlists.PlaylistsFragment
import com.chase.kudzie.chasemusic.ui.playlistsongs.PlaylistSongsFragment
import com.chase.kudzie.chasemusic.ui.songs.SongsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity
}

@Module
abstract class FragmentScreenModule {
    @ContributesAndroidInjector
    abstract fun contributesAlbumsFragment(): AlbumsFragment

    @ContributesAndroidInjector
    abstract fun contributesSongsFragment(): SongsFragment

    @ContributesAndroidInjector
    abstract fun contributesArtistsFragment(): ArtistsFragment

    @ContributesAndroidInjector
    abstract fun contributesNowPlayingFragment(): NowPlayingFragment

    @ContributesAndroidInjector
    abstract fun contributesPlaylistsFragment(): PlaylistsFragment

    @ContributesAndroidInjector
    abstract fun contributesAlbumSongsFragment(): AlbumSongsFragment

    @ContributesAndroidInjector
    abstract fun contributesArtistDetailsFragment(): ArtistDetailsFragment

    @ContributesAndroidInjector
    abstract fun contributesPlaylistSongsFragment(): PlaylistSongsFragment
}