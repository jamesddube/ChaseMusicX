package com.chase.kudzie.chasemusic.injection

import com.chase.kudzie.chasemusic.App
import com.chase.kudzie.chasemusic.injection.module.FragmentScreenModule
import com.chase.kudzie.chasemusic.injection.module.PresentationModule
import com.chase.kudzie.chasemusic.injection.module.UIModule
import com.chase.kudzie.chasemusic.injection.scope.PerApplication
import com.chase.kudzie.chasemusic.shared.injection.SharedComponent
import dagger.Component
import dagger.android.AndroidInjectionModule

@PerApplication
@Component(
    modules = [
        AndroidInjectionModule::class,
        UIModule::class,
        FragmentScreenModule::class,
        PresentationModule::class
    ], dependencies = [SharedComponent::class]
)
interface ApplicationComponent {
    @Component.Factory
    interface Factory {
        fun create(component: SharedComponent): ApplicationComponent
    }

    fun inject(app: App)
}