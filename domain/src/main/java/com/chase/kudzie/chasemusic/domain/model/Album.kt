package com.chase.kudzie.chasemusic.domain.model

data class Album(
    val id: Long,
    val artistId: Long,
    val artistName: String,
    val songCount: Int,
    val title: String,
    val year: Int
)