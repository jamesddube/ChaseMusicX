package com.chase.kudzie.chasemusic.data.repository

import android.content.ContentResolver
import android.content.Context
import com.chase.kudzie.chasemusic.data.extensions.queryAll
import com.chase.kudzie.chasemusic.data.extensions.queryOne
import com.chase.kudzie.chasemusic.data.loaders.AlbumLoader
import com.chase.kudzie.chasemusic.data.mapper.toAlbum
import com.chase.kudzie.chasemusic.domain.model.Album
import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import javax.inject.Inject

class AlbumRepositoryImpl @Inject constructor(
    context: Context
) : AlbumRepository {
    private val contentResolver: ContentResolver = context.contentResolver

    override suspend fun getAlbums(): List<Album> {
        return contentResolver.queryAll(
            AlbumLoader(contentResolver).getAll()
        ) { it.toAlbum() }
    }

    override suspend fun getAlbum(id: Long): Album {
        return contentResolver.queryOne(
            AlbumLoader(contentResolver).getAlbum(id)
        ) { it.toAlbum() }!!
    }

    override suspend fun findAlbums(searchString: String): List<Album> {
        return contentResolver.queryAll(
            AlbumLoader(contentResolver).findAlbums(searchString)
        ) { it.toAlbum() }
    }

    override suspend fun deleteAlbum(id: Long) {
        TODO("Research on multi and single deletes")
    }
}