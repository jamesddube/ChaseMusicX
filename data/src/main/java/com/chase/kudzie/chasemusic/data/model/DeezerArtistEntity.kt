package com.chase.kudzie.chasemusic.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class DeezerArtistEntity(
    @Json(name = "data") var data: List<DeezerDataEntity>,
    @Json(name = "total") var total: Int
)

data class DeezerDataEntity(
    @Json(name = "id") var id: Long,
    @Json(name = "name") var name: String,
    @Json(name = "link") var link: String,
    @Json(name = "picture") var picture: String,
    @Json(name = "picture_small") var pictureSmall: String,
    @Json(name = "picture_medium") var pictureMedium: String,
    @Json(name = "picture_big") var pictureBig: String,
    @Json(name = "picture_xl") var pictureXL: String,
    @Json(name = "type") var type: String
)