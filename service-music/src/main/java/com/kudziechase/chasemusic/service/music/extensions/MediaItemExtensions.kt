package com.kudziechase.chasemusic.service.music.extensions

import com.chase.kudzie.chasemusic.domain.model.Song
import com.kudziechase.chasemusic.service.music.model.MediaItem

internal fun Song.toMediaItem(): MediaItem {
    return MediaItem(
        this.id,
        this.title,
        this.artistName,
        this.albumName,
        this.duration
    )
}