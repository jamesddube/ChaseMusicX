package com.chase.kudzie.chasemusic.domain.repository

import com.chase.kudzie.chasemusic.domain.model.Playlist

interface PlaylistRepository {

    suspend fun getPlaylists(): List<Playlist>

    suspend fun makePlaylist()

    suspend fun deletePlaylist()

}