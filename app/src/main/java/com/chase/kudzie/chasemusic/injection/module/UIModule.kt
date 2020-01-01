package com.chase.kudzie.chasemusic.injection.module

import com.chase.kudzie.chasemusic.MainActivity
import com.chase.kudzie.chasemusic.injection.scope.PerActivity
import com.chase.kudzie.chasemusic.injection.scope.PerApplication
import com.chase.kudzie.chasemusic.injection.scope.PerFragment
import com.chase.kudzie.chasemusic.ui.albums.AlbumsFragment
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
}