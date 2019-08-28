package com.chase.kudzie.chasemusic.domain.interactor.browse.artists

import com.chase.kudzie.chasemusic.domain.repository.ArtistRepository
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class GetArtists @Inject constructor(
    private val repository: ArtistRepository
) {
    suspend operator fun invoke(params: Nothing?) = repository.getArtists()
}