package com.chase.kudzie.chasemusic.service.music.injection

import android.os.Build
import com.chase.kudzie.chasemusic.service.music.extensions.isNougat
import com.chase.kudzie.chasemusic.service.music.extensions.isOreo
import com.chase.kudzie.chasemusic.service.music.injection.scope.PerService
import com.chase.kudzie.chasemusic.service.music.notification.INotificationManager
import com.chase.kudzie.chasemusic.service.music.notification.MediaNotificationV21
import com.chase.kudzie.chasemusic.service.music.notification.MediaNotificationV24
import com.chase.kudzie.chasemusic.service.music.notification.MediaNotificationV26
import dagger.Lazy
import dagger.Module
import dagger.Provides

@Module
internal object MusicNotificationModule {

    @Provides
    @PerService
    @JvmStatic
    fun provideNotificationImplementation(
        mediaNotification: Lazy<MediaNotificationV21>,
        mediaNotificationV24: Lazy<MediaNotificationV24>,
        mediaNotificationV26: Lazy<MediaNotificationV26>
    ): INotificationManager {
        return when {
            isOreo() -> mediaNotificationV26.get() //Android Oreo
            isNougat() -> mediaNotificationV24.get() // Android Nougat
            else -> mediaNotification.get() //Lollipop or greater
        }
    }
}