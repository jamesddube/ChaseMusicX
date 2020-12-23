package com.chase.kudzie.chasemusic.util

import android.content.ContentUris
import android.net.Uri
import java.util.concurrent.TimeUnit

//Gets the location of our album artwork for our music
fun getAlbumArtUri(albumId: Long): Uri =
    ContentUris.withAppendedId(
        Uri.parse("content://media/external/audio/albumart"), albumId
    )

fun makeReadableDuration(millis: Long): String {
    val seconds = TimeUnit.MILLISECONDS.toSeconds(millis) % 60
    val minutes = TimeUnit.MILLISECONDS.toMinutes(millis) % 60
    val hours = TimeUnit.MILLISECONDS.toHours(millis)

    return if (hours > 0L)
        String.format("%02d:%02d:%02d", hours, minutes, seconds)
    else
        String.format("%02d:%02d", minutes, seconds)
}