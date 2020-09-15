package com.chase.kudzie.chasemusic.data.mapper

import android.database.Cursor
import android.provider.BaseColumns
import android.provider.MediaStore
import android.provider.MediaStore.Audio.Media.*
import com.chase.kudzie.chasemusic.data.extensions.*
import com.chase.kudzie.chasemusic.data.extensions.getInt
import com.chase.kudzie.chasemusic.data.extensions.getLong
import com.chase.kudzie.chasemusic.data.extensions.getLongOrNull
import com.chase.kudzie.chasemusic.data.extensions.getStringOrNull
import com.chase.kudzie.chasemusic.domain.model.Album
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.domain.model.Playlist
import com.chase.kudzie.chasemusic.domain.model.Song

/**
 * @author Kudzai A Chasinda
 * */

fun Cursor.toAlbum(): Album {
    val id = getLong(BaseColumns._ID)
    val artistId = getLong(ARTIST_ID)
    val artistName = getStringOrNull(ARTIST) ?: ""
    val title = getStringOrNull(ALBUM) ?: ""
    val year = getInt(MediaStore.Audio.AlbumColumns.FIRST_YEAR)
    val songCount = 0

    return Album(
        id = id,
        artistId = artistId,
        artistName = artistName,
        songCount = songCount,
        title = title,
        year = year
    )
}

fun Cursor.toSong(): Song {
    val id = getLong(BaseColumns._ID)
    val albumName = getStringOrNull(MediaStore.Audio.AudioColumns.ALBUM) ?: ""
    val artistId = getLongOrNull(MediaStore.Audio.AudioColumns.ARTIST_ID) ?: 0L
    val artistName = getStringOrNull(MediaStore.Audio.AudioColumns.ARTIST) ?: ""
    val duration = getLongOrNull(MediaStore.Audio.AudioColumns.DURATION) ?: 0L
    val title = getStringOrNull(MediaStore.Audio.AudioColumns.TITLE) ?: ""
    val trackNumber = getIntOrNull(MediaStore.Audio.AudioColumns.TRACK) ?: 0
    val albumId = getLongOrNull(MediaStore.Audio.AudioColumns.ALBUM_ID) ?: 0L

    return Song(
        id = id,
        albumName = albumName,
        artistId = artistId,
        artistName = artistName,
        duration = duration,
        title = title,
        trackNumber = trackNumber,
        albumId = albumId,
        positionInQueue = -1
    )
}

fun Cursor.toArtist(): Artist {
    val id = getLong(BaseColumns._ID)
    val albumCount = 0
    val name = getStringOrNull(MediaStore.Audio.ArtistColumns.ARTIST) ?: "Unknown Artist"
    val songCount = 0

    return Artist(
        id = id,
        albumCount = albumCount,
        name = name,
        songCount = songCount
    )
}

fun Cursor.toPlaylist(): Playlist {
    val id = getLong(BaseColumns._ID)
    val songCount = 0
    val name = getStringOrNull(MediaStore.Audio.PlaylistsColumns.NAME) ?: ""

    return Playlist(
        id = id,
        name = name,
        songCount = songCount
    )
}