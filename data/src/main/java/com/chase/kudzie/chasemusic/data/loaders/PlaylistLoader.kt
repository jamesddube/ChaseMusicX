package com.chase.kudzie.chasemusic.data.loaders

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns._ID
import android.provider.MediaStore
import android.provider.MediaStore.Audio.PlaylistsColumns

internal class PlaylistLoader(
    private val contentResolver: ContentResolver
) {

    companion object {
        val uri: Uri = MediaStore.Audio.Playlists.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            _ID,
            PlaylistsColumns.NAME
        )

        const val sortOrder = MediaStore.Audio.Playlists.DEFAULT_SORT_ORDER
    }

    @SuppressLint("Recycle")
    fun getAll(): Cursor {
        val selection = null
        val selectionArgs = null

        return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)!!
    }
}