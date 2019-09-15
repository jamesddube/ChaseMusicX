package com.chase.kudzie.chasemusic.injection.module

import com.chase.kudzie.chasemusic.MainActivity
import dagger.Module
import dagger.android.ContributesAndroidInjector

@Module
abstract class UIModule {
    @ContributesAndroidInjector
    abstract fun contributesMainActivity(): MainActivity
}