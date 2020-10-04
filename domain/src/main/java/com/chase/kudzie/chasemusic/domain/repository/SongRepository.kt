package com.chase.kudzie.chasemusic.domain.repository

import com.chase.kudzie.chasemusic.domain.model.Song


interface SongRepository {

    suspend fun getSongs(): List<Song>

    suspend fun findSongs(searchString: String): List<Song>

    suspend fun getSong(id: Long): Song

    suspend fun deleteSong(id: Long)

    suspend fun getSongsByAlbum(albumId: Long): List<Song>

    suspend fun getSongsByArtist(artistId: Long): List<Song>

    suspend fun getSongsByPlaylist(playlistId: Long): List<Song>
}