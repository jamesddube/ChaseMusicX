package com.chase.kudzie.chasemusic.data.repository

import android.content.ContentResolver
import android.content.Context
import com.chase.kudzie.chasemusic.data.extensions.queryAll
import com.chase.kudzie.chasemusic.data.extensions.queryOne
import com.chase.kudzie.chasemusic.data.loaders.ArtistLoader
import com.chase.kudzie.chasemusic.data.mapper.toArtist
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    context: Context
) : ArtistRepository {

    private val contentResolver: ContentResolver = context.contentResolver

    override suspend fun getArtist(id: Long): Artist {
        return contentResolver.queryOne(
            ArtistLoader(contentResolver).getArtist(id)
        ) { it.toArtist() }!!
    }

    override suspend fun getArtists(): List<Artist> {
        return contentResolver.queryAll(
            ArtistLoader(contentResolver).getAll()
        ) { it.toArtist() }
    }

    override suspend fun findArtists(searchString: String): List<Artist> {
        return contentResolver.queryAll(
            ArtistLoader(contentResolver).findArtists(searchString)
        ) { it.toArtist() }
    }
}
