package com.chase.kudzie.chasemusic.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey

/**
 * This class represents a song in a queue
 * */

@Entity(tableName = "queue")
data class QueueSongEntity(
    @PrimaryKey(autoGenerate = true) var id: Int = 0,
    @ColumnInfo(name = "song_id") var songId: Long,
    @ColumnInfo(name = "queue_position") var positionInQueue: Int
)