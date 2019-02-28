package com.chase.kudzie.chasemusic.domain.repository

import com.chase.kudzie.chasemusic.domain.model.Artist
import io.reactivex.Single

interface ArtistRepository {
    //TODO decide whether or not to implement use case for deleting an artist's entire work.

    fun getArtist(id:Long): Single<Artist>

    fun getArtists(): Single<List<Artist>>

    fun findArtists(searchString: String): Single<List<Artist>>
}