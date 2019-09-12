package com.chase.kudzie.chasemusic.domain.repository

import com.chase.kudzie.chasemusic.domain.model.Artist

interface ArtistRepository {

    suspend fun getArtist(id: Long): Artist

    suspend fun getArtists(): List<Artist>

    suspend fun findArtists(searchString: String): List<Artist>
}