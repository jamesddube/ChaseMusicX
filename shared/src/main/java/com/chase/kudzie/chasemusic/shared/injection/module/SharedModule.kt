package com.chase.kudzie.chasemusic.shared.injection.module

import android.app.Application
import android.content.Context
import com.chase.kudzie.chasemusic.domain.scope.ApplicationContext
import dagger.Binds
import dagger.Module

@Module
abstract class SharedModule {
    @Binds
    @ApplicationContext
    internal abstract fun providesContext(instance: Application): Context
}