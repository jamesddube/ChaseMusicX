package com.chase.kudzie.chasemusic.util

import android.content.ContentUris
import android.net.Uri

//Gets the location of our album artwork for our music
fun getAlbumArtUri(albumId: Long): Uri =
    ContentUris.withAppendedId(
        Uri.parse("content://media/external/audio/albumart"), albumId
    )