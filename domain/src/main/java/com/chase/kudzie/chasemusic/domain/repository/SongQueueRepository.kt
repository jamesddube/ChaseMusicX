package com.chase.kudzie.chasemusic.domain.repository

import com.chase.kudzie.chasemusic.domain.model.MediaItem

interface SongQueueRepository {
    suspend fun getQueueSongs(): List<MediaItem>

    suspend fun updateQueue(list: List<MediaItem>)
}