package com.chase.kudzie.chasemusic.domain.model

/**
 * If a song instance does not belong to a Queue then,
 * the positionInQueueIsAlways -1
 * */
data class Song(
    val id: Long,
    val albumName: String,
    val artistId: Long,
    val artistName: String,
    val duration: Long,
    val title: String,
    val trackNumber: Int,
    val albumId: Long,
    val positionInQueue: Int = -1
)