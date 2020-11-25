package com.chase.kudzie.chasemusic.media.model

import com.chase.kudzie.chasemusic.domain.model.MediaId

data class PlayableMediaItem(
    val mediaId: MediaId,
    val title: String,
    val artist: String,
    val positionInQueue: Long
)
