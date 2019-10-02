package com.chase.kudzie.chasemusic.data.repository

import android.content.ContentResolver
import android.content.Context
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.domain.repository.ArtistRepository
import javax.inject.Inject

class ArtistRepositoryImpl @Inject constructor(
    context: Context
) :ArtistRepository{

    private val contentResolver: ContentResolver = context.contentResolver

    override suspend fun getArtist(id: Long): Artist {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getArtists(): List<Artist> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findArtists(searchString: String): List<Artist> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}
