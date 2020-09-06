package com.chase.kudzie.chasemusic.data.repository

import com.chase.kudzie.chasemusic.data.database.ChaseMusicDatabase
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.domain.repository.SongQueueRepository
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import java.util.Collections.copy
import javax.inject.Inject

class SongQueueRepositoryImpl @Inject constructor(
    database: ChaseMusicDatabase,
    private val songRepository: SongRepository
) : SongQueueRepository {

    private val queueDao = database.queueDao()

    override suspend fun getQueueSongs(): List<Song> {
        //Returns an existing queue or make one
        val queue = queueDao.getQueuedSongs(songRepository.getSongs())
        if (queue.isNotEmpty()) {
            return queue
        }
        return songRepository.getSongs()
    }

    override suspend fun updateQueue(list: List<Song>) {
        return queueDao.insertIntoQueue(list)
    }

}