package com.chase.kudzie.chasemusic.service.music.extensions

import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_BROWSABLE
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_PLAYABLE
import android.support.v4.media.MediaDescriptionCompat
import com.chase.kudzie.chasemusic.domain.model.*


fun Song.toMediaBrowserItem(): MediaBrowserCompat.MediaItem {
    val description = MediaDescriptionCompat.Builder()
        .setMediaId(MediaId.SONGS.toString())
        .setTitle(this.title)
        .setSubtitle(this.albumName)
        .setDescription(this.artistName)
        .build()

    return MediaBrowserCompat.MediaItem(description, FLAG_PLAYABLE)
}

fun Artist.toMediaBrowserItem(): MediaBrowserCompat.MediaItem {
    val description = MediaDescriptionCompat.Builder()
        .setMediaId(MediaId.ARTISTS.toString())
        .setTitle(this.name)
        .build()

    return MediaBrowserCompat.MediaItem(description, FLAG_BROWSABLE)
}

fun Album.toMediaBrowserItem(): MediaBrowserCompat.MediaItem {
    val description = MediaDescriptionCompat.Builder()
        .setMediaId(MediaId.ALBUMS.toString())
        .setTitle(this.title)
        .setSubtitle(this.artistName)
        .build()

    return MediaBrowserCompat.MediaItem(description, FLAG_BROWSABLE)
}

fun Playlist.toMediaBrowserItem(): MediaBrowserCompat.MediaItem {
    val description = MediaDescriptionCompat.Builder()
        .setMediaId(MediaId.PLAYLISTS.toString())
        .setTitle(this.name)
        .build()

    return MediaBrowserCompat.MediaItem(description, FLAG_BROWSABLE)
}

fun Song.toChildMediaBrowserItem(mediaIdCategory: MediaIdCategory): MediaBrowserCompat.MediaItem {
    val description = MediaDescriptionCompat.Builder()
        .setMediaId(mediaIdCategory.toString())
        .setTitle(this.title)
        .setSubtitle(this.albumName)
        .setDescription(this.artistName)
        .build()

    return MediaBrowserCompat.MediaItem(description, FLAG_PLAYABLE)
}