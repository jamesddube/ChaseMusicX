package com.chase.kudzie.chasemusic.injection.module

import android.app.Application
import android.content.Context
import com.chase.kudzie.chasemusic.domain.scope.ApplicationContext
import dagger.Binds
import dagger.Module

@Module
abstract class ApplicationModule {
    @Binds
    @ApplicationContext
    abstract fun bindContext(application: Application): Context
}