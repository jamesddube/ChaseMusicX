package com.chase.kudzie.chasemusic.domain.model

data class Song(
    val id: Long,
    val albumName: String,
    val artistId: Long,
    val artistName: String,
    val duration: Long,
    val title: String,
    val trackNumber: Int,
    val albumId: Long
)