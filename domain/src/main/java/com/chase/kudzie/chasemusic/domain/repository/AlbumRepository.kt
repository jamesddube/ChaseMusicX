package com.chase.kudzie.chasemusic.domain.repository

import com.chase.kudzie.chasemusic.domain.model.Album
import io.reactivex.Completable
import io.reactivex.Single

interface AlbumRepository {

    fun getAlbums(): List<Album>

    fun getAlbum(id: Long): Single<Album>

    fun findAlbums(searchString: String): Single<List<Album>>

    fun deleteAlbum(id: Long): Completable //Umm this might return something
}