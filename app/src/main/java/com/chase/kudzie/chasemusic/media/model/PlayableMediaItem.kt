package com.chase.kudzie.chasemusic.media.model

import android.net.Uri
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory

data class PlayableMediaItem(
    val mediaId: MediaIdCategory,
    val title: String,
    val artist: String,
    val artWorkUri: Uri?,
    val positionInQueue: Long
)
