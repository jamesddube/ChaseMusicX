package com.chase.kudzie.chasemusic.service.music.model

data class MediaItem(
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val albumId: Long
)



