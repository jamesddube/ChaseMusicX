package com.chase.kudzie.chasemusic.service.music.extensions

import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.domain.model.MediaItem
import com.chase.kudzie.chasemusic.service.music.model.PlayableMediaItem

internal fun Song.toMediaItem(mediaId: MediaIdCategory): MediaItem {
    return MediaItem(
        mediaId,
        this.id,
        this.title,
        this.artistName,
        this.albumName,
        this.duration,
        this.albumId,
        this.positionInQueue
    )
}

internal fun MediaItem.toPlayableMediaItem(): PlayableMediaItem {
    return PlayableMediaItem(this)
}
