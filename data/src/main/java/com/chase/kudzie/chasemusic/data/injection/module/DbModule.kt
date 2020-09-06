package com.chase.kudzie.chasemusic.data.injection.module

import android.content.Context
import androidx.room.Room
import com.chase.kudzie.chasemusic.data.database.ChaseMusicDatabase
import com.chase.kudzie.chasemusic.domain.scope.ApplicationContext
import dagger.Module
import dagger.Provides
import javax.inject.Singleton

@Module
abstract class DbModule {

    @Module
    companion object {
        @Provides
        @JvmStatic
        @Singleton
        fun providesRoomDatabase(@ApplicationContext context: Context): ChaseMusicDatabase {
            return Room.databaseBuilder(
                context,
                ChaseMusicDatabase::class.java,
                "chasemusic_db"
            ).build()
        }
    }

}