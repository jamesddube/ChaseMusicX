package com.chase.kudzie.chasemusic.data.repository

import com.chase.kudzie.chasemusic.data.database.ChaseMusicDatabase
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.domain.model.MediaItem
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

    override suspend fun getQueueSongs(): List<MediaItem> {
        //Returns an existing queue or make one
        val queue =
            queueDao.getQueuedSongs(songRepository.getSongs().map { song -> song.toMediaItem() })
        if (queue.isNotEmpty()) {
            return queue
        }
        return songRepository.getSongs().map { song -> song.toMediaItem() }
    }

    override suspend fun updateQueue(list: List<MediaItem>) {
        return queueDao.insertIntoQueue(list)
    }

}

internal fun Song.toMediaItem(): MediaItem {
    return MediaItem(
        MediaIdCategory.makeSongsCategory(this.id),
        this.id,
        this.title,
        this.artistName,
        this.albumName,
        this.duration,
        this.albumId,
        this.positionInQueue
    )
}