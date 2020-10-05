package com.chase.kudzie.chasemusic

import android.app.Activity
import android.app.Application
import android.os.Build
import androidx.appcompat.app.AppCompatDelegate
import androidx.multidex.MultiDex
import com.chase.kudzie.chasemusic.injection.DaggerApplicationComponent
import com.chase.kudzie.chasemusic.shared.injection.SharedComponent
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class App : Application(), HasActivityInjector {

    @Inject
    lateinit var androidInjector: DispatchingAndroidInjector<Activity>

    override fun activityInjector(): AndroidInjector<Activity> {
        return androidInjector
    }

    override fun onCreate() {
        super.onCreate()
        // for enabling night mode in auto mode

        val nightMode = if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.Q) {
            AppCompatDelegate.MODE_NIGHT_FOLLOW_SYSTEM
        } else {
            AppCompatDelegate.MODE_NIGHT_AUTO_BATTERY
        }
        AppCompatDelegate.setDefaultNightMode(nightMode)


        initTimber()
        initDagger()
        MultiDex.install(this)
    }

    private fun initTimber() {
        Timber.plant(Timber.DebugTree())
    }

    private fun initDagger() {
        DaggerApplicationComponent
            .factory()
            .create(SharedComponent.sharedComponent(this))
            .inject(this)
    }
}