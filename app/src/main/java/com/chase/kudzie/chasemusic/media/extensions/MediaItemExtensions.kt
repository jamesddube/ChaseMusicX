package com.chase.kudzie.chasemusic.media.extensions

import android.support.v4.media.session.MediaSessionCompat
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.media.model.PlayableMediaItem


fun MediaSessionCompat.QueueItem.toPlayableItem(): PlayableMediaItem {
    return PlayableMediaItem(
        mediaId = MediaIdCategory.fromString(this.description.mediaId!!).mediaId,
        title = this.description.title.toString(),
        artist = this.description.subtitle.toString(),
        positionInQueue = this.queueId
    )
}