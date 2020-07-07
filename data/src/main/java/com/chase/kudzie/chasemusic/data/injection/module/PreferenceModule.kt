package com.chase.kudzie.chasemusic.data.injection.module

import android.content.Context
import android.content.SharedPreferences
import com.chase.kudzie.chasemusic.data.preferences.SharedPreferencesFactory
import com.chase.kudzie.chasemusic.data.repository.PreferencesRepositoryImpl
import com.chase.kudzie.chasemusic.domain.repository.PreferencesRepository
import com.chase.kudzie.chasemusic.domain.scope.ApplicationContext
import dagger.Binds
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class PreferenceModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun providesSharedPreferences(@ApplicationContext context: Context): SharedPreferences {
            return SharedPreferencesFactory.makeChaseMusicPreferences(context)
        }
    }

    @Binds
    @Singleton
    abstract fun providesPreferences(preferencesRepository: PreferencesRepositoryImpl): PreferencesRepository

}