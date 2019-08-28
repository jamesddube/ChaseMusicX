package com.chase.kudzie.chasemusic.domain.repository

import com.chase.kudzie.chasemusic.domain.model.Album
import io.reactivex.Completable
import io.reactivex.Single

interface AlbumRepository {

    suspend fun getAlbums(): List<Album>

    suspend fun getAlbum(id: Long): Album

    suspend fun findAlbums(searchString: String): List<Album>

    suspend fun deleteAlbum(id: Long)
}