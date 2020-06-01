package com.chase.kudzie.chasemusic.domain.repository

import com.chase.kudzie.chasemusic.domain.model.Song

interface SongQueueRepository {
    suspend fun getQueueSongs(): List<Song>
}