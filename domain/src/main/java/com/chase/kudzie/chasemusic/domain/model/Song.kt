package com.chase.kudzie.chasemusic.domain.model

data class Song(
    val id: Long,
    val albumName: String,
    val artistId: Long,
    val artistName: String,
    val duration: Int,
    val title: String,
    val trackNumber: Int
)