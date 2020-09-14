package com.chase.kudzie.chasemusic.domain.model

/**
 * @author Kudzai Chasinda
 * */
data class Artist(
    val id: Long,
    val albumCount: Int,
    val name: String,
    val albumArtist:String,
    val songCount: Int
)