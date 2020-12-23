package com.chase.kudzie.chasemusic.domain.model


data class MediaItem(
    val mediaId: MediaIdCategory,
    val id: Long,
    val title: String,
    val artist: String,
    val album: String,
    val duration: Long,
    val albumId: Long,
    val positionInQueue:Int
)



