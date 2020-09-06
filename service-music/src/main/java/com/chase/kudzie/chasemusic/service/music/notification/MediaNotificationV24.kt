package com.chase.kudzie.chasemusic.service.music.notification

import android.annotation.SuppressLint
import android.content.ContentUris
import android.net.Uri
import android.support.v4.media.session.MediaSessionCompat
import android.text.SpannableString
import com.chase.kudzie.chasemusic.service.music.MusicService
import com.chase.kudzie.chasemusic.service.music.extensions.retrieveGlideBitmap
import javax.inject.Inject

@SuppressLint("RestrictedApi")
internal open class MediaNotificationV24 @Inject constructor(
    service: MusicService,
    mediaSession: MediaSessionCompat
) : MediaNotificationV21(service, mediaSession) {

    override suspend fun updateMetadataImpl(
        id: Long,
        title: SpannableString,
        artist: String,
        album: String,
        albumId: Long
    ) {
        builder.mActions[0] = Actions.skipToPreviousAction(service)
        builder.mActions[2] = Actions.skipToNextAction(service)

        val albumArt = service.retrieveGlideBitmap(getAlbumArtUri(albumId))

        builder.setLargeIcon(albumArt)
            .setContentTitle(title)
            .setContentText(artist)
            .setSubText(album)
    }

    private fun getAlbumArtUri(albumId: Long): Uri =
        ContentUris.withAppendedId(
            Uri.parse("content://media/external/audio/albumart"), albumId
        )
}