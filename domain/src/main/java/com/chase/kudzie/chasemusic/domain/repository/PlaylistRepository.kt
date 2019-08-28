package com.chase.kudzie.chasemusic.domain.repository

import io.reactivex.Completable

interface PlaylistRepository {

    suspend fun makePlaylist()

    suspend fun deletePlaylist()

}