package com.chase.kudzie.chasemusic.data.database.dao

import androidx.room.*
import com.chase.kudzie.chasemusic.data.database.entities.QueueSongEntity
import com.chase.kudzie.chasemusic.domain.model.Song

@Dao
abstract class QueueDao {

    @Query("SELECT * FROM queue")
    abstract suspend fun getAll(): List<QueueSongEntity>

    @Insert
    abstract suspend fun insertSongs(list: List<QueueSongEntity>)

    @Query("DELETE FROM queue")
    abstract suspend fun clearAll()

    @Transaction
    open suspend fun insertIntoQueue(list: List<Song>) {
        //Clear all songs first
        clearAll()
        val queueEntities = list.map {
            QueueSongEntity(
                songId = it.id,
                positionInQueue = it.positionInQueue
            )
        }
        insertSongs(queueEntities)
    }

    suspend fun getQueuedSongs(songList: List<Song>): List<Song> {
        val queueList = getAll()
        return createSongQueue(queueList, songList)
    }

    private fun createSongQueue(
        queueList: List<QueueSongEntity>,
        songList: List<Song>
    ): List<Song> {
        val songArr = songList.groupBy { it.id } // [1,2,4,77]

        val filteredSongsList = mutableListOf<Song>()

        for (item in queueList) {
            val id = item.songId
            val song = (songArr[id] ?: continue)[0]
            filteredSongsList.add(song)
        }
        return filteredSongsList
    }
}