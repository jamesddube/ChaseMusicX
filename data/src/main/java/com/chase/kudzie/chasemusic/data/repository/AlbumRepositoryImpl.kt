package com.chase.kudzie.chasemusic.data.repository

import android.content.ContentResolver
import android.content.Context
import com.chase.kudzie.chasemusic.data.extensions.queryAll
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
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findAlbums(searchString: String): List<Album> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteAlbum(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}