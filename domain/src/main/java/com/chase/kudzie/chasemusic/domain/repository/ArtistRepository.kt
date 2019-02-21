package com.chase.kudzie.chasemusic.domain.repository

import com.chase.kudzie.chasemusic.domain.model.Artist
import io.reactivex.Single

interface ArtistRepository {

    fun getArtist(): Single<Artist>

    fun getArtists(): Single<List<Artist>>

    fun findArtists(): Single<List<Artist>>
}