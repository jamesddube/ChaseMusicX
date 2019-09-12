package com.chase.kudzie.chasemusic.domain.repository

interface PlaylistRepository {

    suspend fun makePlaylist()

    suspend fun deletePlaylist()

}