package com.chase.kudzie.chasemusic.injection.component

import android.app.Application
import android.content.Context
import com.chase.kudzie.chasemusic.images.ChaseMusicGlideModule
import com.chase.kudzie.chasemusic.injection.scope.PerImage
import com.chase.kudzie.chasemusic.shared.injection.SharedComponent
import dagger.Component

@Component(dependencies = [SharedComponent::class])
@PerImage
interface ImageComponent {
    fun inject(instance: ChaseMusicGlideModule)

    @Component.Factory
    interface Factory {
        fun create(component: SharedComponent): ImageComponent
    }
}

fun ChaseMusicGlideModule.inject(context: Context){
    DaggerImageComponent.factory()
        .create(SharedComponent.sharedComponent(context.applicationContext as Application))
        .inject(this)
}
