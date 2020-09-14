package com.chase.kudzie.chasemusic.model

data class DeezerArtistView(
    val data: List<DeezerDataView>,
    val total: Int
)

data class DeezerDataView(
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