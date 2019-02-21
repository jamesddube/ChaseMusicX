package com.chase.kudzie.chasemusic.domain.repository

import io.reactivex.Completable

interface PlaylistRepository {

    fun makePlaylist(): Completable //Change to completable use case

    fun deletePlaylist(): Completable

}