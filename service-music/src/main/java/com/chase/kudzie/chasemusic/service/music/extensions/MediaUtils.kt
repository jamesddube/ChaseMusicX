package com.chase.kudzie.chasemusic.service.music.extensions

import android.content.ContentUris
import android.net.Uri
import android.os.Build


fun isNougat(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.N

fun isOreo(): Boolean = Build.VERSION.SDK_INT >= Build.VERSION_CODES.O

fun getAlbumArtUri(albumId: Long): Uri =
    ContentUris.withAppendedId(
        Uri.parse("content://media/external/audio/albumart"), albumId
    )

