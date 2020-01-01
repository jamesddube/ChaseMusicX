package com.kudziechase.chasemusic.service.music.injection

import com.kudziechase.chasemusic.service.music.MusicService
import com.kudziechase.chasemusic.service.music.injection.scope.PerService
import com.kudziechase.chasemusic.shared.injection.SharedComponent
import dagger.BindsInstance
import dagger.Component

@PerService
@Component(
    modules = [
        MusicServiceModule::class
    ], dependencies = [SharedComponent::class]
)
interface MusicServiceComponent {
    fun inject(service: MusicService)

    @Component.Factory
    interface Factory {
        fun create(@BindsInstance service: MusicService, component: SharedComponent): MusicServiceComponent
    }
}


internal fun MusicService.inject() {
    val sharedComponent = SharedComponent.sharedComponent(application)
    DaggerMusicServiceComponent.factory()
        .create(this, sharedComponent)
        .inject(this)
}