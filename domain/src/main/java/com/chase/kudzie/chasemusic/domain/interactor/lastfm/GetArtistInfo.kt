package com.chase.kudzie.chasemusic.domain.interactor.lastfm

import com.chase.kudzie.chasemusic.domain.model.LastFMArtist
import com.chase.kudzie.chasemusic.domain.repository.LastFMRepository
import javax.inject.Inject

class GetArtistInfo @Inject constructor(
    private val repository: LastFMRepository
) {
    suspend operator fun invoke(artistName: String): LastFMArtist =
        repository.getArtistInfo(artistName)
}