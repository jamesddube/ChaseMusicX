package com.chase.kudzie.chasemusic.data.loaders

import android.annotation.SuppressLint
import android.content.ContentResolver
import android.database.Cursor
import android.net.Uri
import android.provider.BaseColumns._ID
import android.provider.MediaStore
import android.provider.MediaStore.Audio.ArtistColumns.*
import android.provider.MediaStore.Audio.AudioColumns
import com.chase.kudzie.chasemusic.data.sorting.SortOrder
import com.chase.kudzie.chasemusic.data.sorting.SortOrder.AlbumSortOrder.Companion.ALBUM_ARTIST

internal class ArtistLoader(
    private val contentResolver: ContentResolver
) {

    companion object {
        val uri: Uri = MediaStore.Audio.Artists.EXTERNAL_CONTENT_URI

        val projection = arrayOf(
            _ID,
            ARTIST,
            NUMBER_OF_ALBUMS,
            "album_artist",
            NUMBER_OF_TRACKS
        )

        const val sortOrder = SortOrder.ArtistSortOrder.ARTIST_A_Z
    }

    @SuppressLint("Recycle")
    fun getAll(): Cursor {
        val selection = null
        val selectionArgs = null

        return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)!!
    }

    @SuppressLint("Recycle")
    fun getArtist(id: Long): Cursor {
        val selection = "$_ID=?"
        val selectionArgs = arrayOf(
            "$id"
        )

        return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)!!
    }

    @SuppressLint("Recycle")
    fun findArtists(searchString: String): Cursor {
        val selection = "$ARTIST LIKE?"
        val selectionArgs = arrayOf(
            "%$searchString%"
        )

        return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)!!
    }


    @SuppressLint("Recycle")
    fun getArtistAlbums(id: Long): Cursor {
        val uri = MediaStore.Audio.Artists.Albums.getContentUri("external", id)
        val projection = AlbumLoader.projection
        val sortOrder = AlbumLoader.sortOrder
        val selection = null
        val selectionArgs = null

        return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)!!
    }

    @SuppressLint("Recycle")
    fun getArtistSongs(id: Long): Cursor {
        val selection =
            "${AudioColumns.IS_MUSIC} = 1 " +
                    "AND ${AudioColumns.TITLE} != '' " +
                    "AND ${AudioColumns.ARTIST_ID}=$id"
        val selectionArgs = null
        val uri = SongLoader.uri
        val projection = SongLoader.projection
        val sortOrder = SongLoader.sortOrder

        return contentResolver.query(uri, projection, selection, selectionArgs, sortOrder)!!
    }
}