package com.chase.kudzie.chasemusic.domain.repository

import com.chase.kudzie.chasemusic.domain.model.Song
import io.reactivex.Completable
import io.reactivex.Single

interface SongRepository {

    fun getSongs(): Single<List<Song>>

    fun findSongs(searchString: String): Single<List<Song>>

    fun getSong(id:Long): Single<Song>

    fun deleteSong(id:Long): Completable

}