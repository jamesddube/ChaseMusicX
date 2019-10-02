package com.chase.kudzie.chasemusic.data.repository

import android.content.ContentResolver
import android.content.Context
import com.chase.kudzie.chasemusic.domain.repository.PlaylistRepository
import javax.inject.Inject

class PlaylistRepositoryImpl @Inject constructor(
    context: Context
):PlaylistRepository{

    private val contentResolver: ContentResolver = context.contentResolver

    override suspend fun deletePlaylist() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }

    override suspend fun makePlaylist() {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}