package com.chase.kudzie.chasemusic.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import com.chase.kudzie.chasemusic.data.database.dao.QueueDao
import com.chase.kudzie.chasemusic.data.database.entities.QueueSongEntity

@Database(
    entities = [QueueSongEntity::class],
    version = 1,
    exportSchema = true
)
abstract class ChaseMusicDatabase : RoomDatabase() {
    abstract fun queueDao(): QueueDao
}