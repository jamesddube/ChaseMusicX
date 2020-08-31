package com.chase.kudzie.chasemusic.domain.model

data class LastFMArtist(val artist: LFMArtist)

data class LFMArtist(
    val name: String,
    val image: List<LFMImage>,
    val stats: Stats,
    val bio: Bio
)

data class LFMImage(val text: String, val size: String)

data class Stats(val listeners: String, val playCount: String)

data class Bio(val summary: String, val content: String)