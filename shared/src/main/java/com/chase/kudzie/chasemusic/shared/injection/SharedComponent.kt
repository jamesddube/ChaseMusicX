package com.chase.kudzie.chasemusic.shared.injection

import android.app.Application
import android.content.Context
import com.chase.kudzie.chasemusic.domain.repository.*
import com.chase.kudzie.chasemusic.shared.injection.module.SharedModule
import com.chase.kudzie.chasemusic.domain.scope.ApplicationContext
import com.chase.kudzie.chasemusic.data.injection.module.DataModule
import com.chase.kudzie.chasemusic.data.injection.module.DbModule
import com.chase.kudzie.chasemusic.data.injection.module.PreferenceModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SharedModule::class,
        DataModule::class,
        PreferenceModule::class,
        DbModule::class
    ]
)
interface SharedComponent {
    //A bit hacky

    @ApplicationContext
    fun context(): Context

    fun albumRepository(): AlbumRepository
    fun songRepository(): SongRepository
    fun artistRepository(): ArtistRepository
    fun playlistRepository(): PlaylistRepository
    fun preferencesRepository(): PreferencesRepository
    fun songQueueRepository(): SongQueueRepository
    fun lastFMRepository(): LastFMRepository

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): SharedComponent
    }

    companion object {
        private var component: SharedComponent? = null

        @JvmStatic
        fun sharedComponent(app: Application): SharedComponent {
            if (component == null) {
                component = DaggerSharedComponent.factory()
                    .create(app)
            }

            return component!!
        }
    }


}