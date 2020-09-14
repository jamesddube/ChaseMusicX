package com.chase.kudzie.chasemusic.domain.interactor.deezer

import com.chase.kudzie.chasemusic.domain.model.DeezerArtist
import com.chase.kudzie.chasemusic.domain.repository.DeezerRepository
import javax.inject.Inject

class GetArtistInformation @Inject constructor(
    private val repository: DeezerRepository
) {
    suspend operator fun invoke(artistName: String): DeezerArtist =
        repository.getArtistInformation(artistName)
}