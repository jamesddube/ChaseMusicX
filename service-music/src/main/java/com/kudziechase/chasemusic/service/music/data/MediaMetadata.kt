package com.kudziechase.chasemusic.service.music.data

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import com.kudziechase.chasemusic.service.music.model.MediaItem
import javax.inject.Inject

class MediaMetadata @Inject constructor(
    private val mediaSession: MediaSessionCompat
) : MediaMetadataListener {

    override fun onMetadataChanged(mediaItem: MediaItem) {
        updateMediaSessionMetadata(mediaItem)
    }

    fun updateMediaSessionMetadata(entity: MediaItem) {
        val metadataBuilder = MediaMetadataCompat.Builder()

        metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, entity.artist)
            .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, entity.album)
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, entity.title)
            .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, entity.duration)
            .putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, null)

            .build()

        mediaSession.setMetadata(metadataBuilder.build())
    }
}