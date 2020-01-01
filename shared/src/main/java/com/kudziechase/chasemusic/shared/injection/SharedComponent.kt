package com.kudziechase.chasemusic.shared.injection

import android.app.Application
import android.content.Context
import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import com.chase.kudzie.chasemusic.domain.repository.ArtistRepository
import com.chase.kudzie.chasemusic.domain.repository.PlaylistRepository
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import com.kudziechase.chasemusic.shared.injection.module.SharedModule
import com.chase.kudzie.chasemusic.domain.scope.ApplicationContext
import com.kudziechase.chasemusic.shared.injection.module.DataModule
import dagger.BindsInstance
import dagger.Component
import javax.inject.Singleton

@Singleton
@Component(
    modules = [
        SharedModule::class,
        DataModule::class
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

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance app: Application): SharedComponent
    }

    companion object {
        private var component: SharedComponent? = null

        @JvmStatic
        fun sharedComponent(app: Application): SharedComponent {
            if (component == null) {
                component = DaggerSharedComponent.factory().create(app)
            }

            return component!!
        }
    }


}