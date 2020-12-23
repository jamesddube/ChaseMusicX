package com.chase.kudzie.chasemusic.domain.model


data class DeezerArtist(
    val data: List<DeezerData>,
    val total: Int
)

data class DeezerData(
    val id: Long,
    val name: String,
    val link: String,
    val picture: String,
    val pictureSmall: String,
    val pictureMedium: String,
    val pictureBig: String,
    val pictureXL: String,
    val type: String
)