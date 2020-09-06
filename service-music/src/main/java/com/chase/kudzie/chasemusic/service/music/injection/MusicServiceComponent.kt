package com.chase.kudzie.chasemusic.service.music.injection

import com.chase.kudzie.chasemusic.service.music.MusicService
import com.chase.kudzie.chasemusic.service.music.injection.scope.PerService
import com.chase.kudzie.chasemusic.shared.injection.SharedComponent
import dagger.BindsInstance
import dagger.Component

@PerService
@Component(
    modules = [
        MusicServiceModule::class,
        MusicNotificationModule::class
    ], dependencies = [SharedComponent::class]
)
interface MusicServiceComponent {
    fun inject(service: MusicService)

    @Component.Factory
    interface Factory {
        fun create(
            @BindsInstance service: MusicService,
            component: SharedComponent
        ): MusicServiceComponent
    }
}


internal fun MusicService.inject() {
    val sharedComponent = SharedComponent.sharedComponent(application)
    DaggerMusicServiceComponent.factory()
        .create(this, sharedComponent)
        .inject(this)
}