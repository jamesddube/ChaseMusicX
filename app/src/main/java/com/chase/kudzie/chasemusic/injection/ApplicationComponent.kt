package com.chase.kudzie.chasemusic.injection

import android.app.Application
import com.chase.kudzie.chasemusic.App
import com.chase.kudzie.chasemusic.injection.module.ApplicationModule
import com.chase.kudzie.chasemusic.injection.module.DataModule
import com.chase.kudzie.chasemusic.injection.module.PresentationModule
import com.chase.kudzie.chasemusic.injection.module.UIModule
import dagger.BindsInstance
import dagger.Component
import dagger.android.AndroidInjectionModule
import javax.inject.Singleton

@Singleton
@Component(
    modules = arrayOf(
        AndroidInjectionModule::class,
        ApplicationModule::class,
        UIModule::class,
        PresentationModule::class,
        DataModule::class
    )
)
interface ApplicationComponent {
    @Component.Builder
    interface Builder {
        @BindsInstance
        fun application(application: Application): Builder
        fun build(): ApplicationComponent
    }

    fun inject(app: App)

}