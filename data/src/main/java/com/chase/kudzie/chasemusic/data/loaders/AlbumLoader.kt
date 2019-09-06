package com.chase.kudzie.chasemusic.data.loaders

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns.*
import android.provider.MediaStore.Audio.AlbumColumns.*

import android.provider.MediaStore.Audio.Albums.EXTERNAL_CONTENT_URI
import com.chase.kudzie.chasemusic.data.sorting.SortOrder

internal class AlbumLoader(
    private val contentResolver: ContentResolver
) {

    companion object {
        val uri: Uri = EXTERNAL_CONTENT_URI
        val projection = arrayOf(
            _ID,
            ALBUM_ID,
            ALBUM,
            NUMBER_OF_SONGS,
            FIRST_YEAR,
            ARTIST,
            ARTIST_ID
        )
        val sortOrder = SortOrder.AlbumSortOrder.ALBUM_A_Z
    }

    @SuppressLint("Recycle")
    fun getAll(): Cursor {
        val selection = null
        val selectionArgs = null

        return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)!!
    }

    @SuppressLint("Recycle")
    fun getAlbumSongs(id: Long): Cursor {
        val selection = "$_ID=?"
        val selectionArgs = null

        return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)!!
    }

    @SuppressLint("Recycle")
    fun findAlbums(searchString: String): Cursor {
        val selection = "$_ID=?"
        val selectionArgs = arrayOf(
            "%$searchString%"
        )

        return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)!!
    }
}