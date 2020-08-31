package com.chase.kudzie.chasemusic.domain.interactor.albums

import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import javax.inject.Inject

class GetAlbumsByArtist @Inject constructor(
    private val repository: AlbumRepository
) {
    suspend operator fun invoke(artistId: Long) = repository.getAlbumsByArtist(artistId)
}