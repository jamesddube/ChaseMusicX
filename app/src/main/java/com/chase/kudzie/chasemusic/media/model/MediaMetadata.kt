package com.chase.kudzie.chasemusic.media.model

import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.MediaMetadataCompat.*
import com.chase.kudzie.chasemusic.domain.model.MediaConstants.METADATA_KEY_ALBUM_ID
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory

class MediaMetadata(private val metadata: MediaMetadataCompat) {

    val mediaIdCategory: MediaIdCategory
        get() = MediaIdCategory
            .fromString(metadata.getString(METADATA_KEY_MEDIA_ID))

    val artist: String = metadata.getString(METADATA_KEY_ARTIST)
    val title: String = metadata.getString(METADATA_KEY_TITLE)
    val album: String = metadata.getString(METADATA_KEY_ALBUM)
    val duration: Long = metadata.getLong(METADATA_KEY_DURATION)
    val albumId: Long = metadata.getLong(METADATA_KEY_ALBUM_ID)
    val songId: Long = mediaIdCategory.songId
}