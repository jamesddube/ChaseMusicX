package com.chase.kudzie.chasemusic.domain.interactor.artists

import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.domain.repository.ArtistRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class FindArtists @Inject constructor(
    private val repository: ArtistRepository
) {
    suspend operator fun invoke(params: Params?): List<Artist> {
        if (params == null) throw IllegalArgumentException("Param searchString cannot be null")
        return repository.findArtists(params.searchString)
    }


    data class Params(val searchString: String) {
        companion object {
            fun forArtist(searchString: String): Params = Params(searchString)
        }
    }
}