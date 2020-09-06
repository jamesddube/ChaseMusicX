package com.chase.kudzie.chasemusic.data.model

import com.squareup.moshi.Json
import com.squareup.moshi.JsonClass

@JsonClass(generateAdapter = true)
data class LastFMArtistEntity(@Json(name = "artist") val artist: LFMArtistEntity)

data class LFMArtistEntity(
    @Json(name = "name") val name: String,
    @Json(name = "image") val image: List<LFMImageEntity>,
    @Json(name = "stats") val stats: StatsEntity,
    @Json(name = "bio") val bio: BioEntity
)

data class LFMImageEntity(
    @Json(name = "#text") val text: String,
    @Json(name = "size") val size: String
)

data class StatsEntity(
    @Json(name = "listeners") val listeners: String,
    @Json(name = "playcount") val playCount: String
)

data class BioEntity(
    @Json(name = "summary") val summary: String,
    @Json(name = "content") val content: String
)