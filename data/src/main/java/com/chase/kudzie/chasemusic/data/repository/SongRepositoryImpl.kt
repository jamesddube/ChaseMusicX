package com.chase.kudzie.chasemusic.data.repository

import android.content.ContentResolver
import android.content.Context
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    context: Context
) : SongRepository {
    private val contentResolver: ContentResolver = context.contentResolver

    override suspend fun getSongs(): List<Song> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun findSongs(searchString: String): List<Song> {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun getSong(id: Long): Song {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun deleteSong(id: Long) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}