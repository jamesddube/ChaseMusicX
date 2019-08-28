package com.chase.kudzie.chasemusic.domain.repository

import com.chase.kudzie.chasemusic.domain.model.Song


interface SongRepository {

    suspend fun getSongs(): List<Song>

    suspend fun findSongs(searchString: String): List<Song>

    suspend fun getSong(id: Long): Song

    suspend fun deleteSong(id: Long)

}