package com.chase.kudzie.chasemusic.data.mapper

import android.database.Cursor
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media.*
import com.chase.kudzie.chasemusic.data.extensions.getInt
import com.chase.kudzie.chasemusic.data.extensions.getLong
import com.chase.kudzie.chasemusic.data.extensions.getStringOrNull
import com.chase.kudzie.chasemusic.domain.model.Album


fun Cursor.toAlbum(): Album {
    val id = getLong(ALBUM_ID)
    val artistId = getLong(ARTIST_ID)
    val artistName = getStringOrNull(ARTIST) ?: ""
    val title = getStringOrNull(ALBUM) ?: ""
    val year = getInt(MediaStore.Audio.AlbumColumns.FIRST_YEAR)
    val songCount = 0

    return Album(
        id, artistId, artistName, songCount, title, year
    )
}