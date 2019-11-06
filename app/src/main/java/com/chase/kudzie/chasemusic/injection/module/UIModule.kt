package com.chase.kudzie.chasemusic.injection.module

import com.chase.kudzie.chasemusic.MainActivity
import com.chase.kudzie.chasemusic.ui.albums.AlbumsFragment
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {
    @ContributesAndroidInjector(modules = [FragmentScreenModule::class])
    abstract fun contributesMainActivity(): MainActivity
}

@Module
abstract class FragmentScreenModule {
    @ContributesAndroidInjector
    abstract fun contributesAlbumsFragment(): AlbumsFragment
}