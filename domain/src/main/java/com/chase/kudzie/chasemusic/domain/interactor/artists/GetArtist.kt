package com.chase.kudzie.chasemusic.domain.interactor.artists

import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.domain.repository.ArtistRepository
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class GetArtist @Inject constructor(
    private val repository: ArtistRepository
) {
    suspend operator fun invoke(params: Params?): Artist {
        if (params == null) throw IllegalArgumentException("Param id cannot be null")
        return repository.getArtist(params.id)
    }

    data class Params(val id: Long) {
        companion object {
            fun forArtist(id: Long): Params = Params(id)
        }
    }
}