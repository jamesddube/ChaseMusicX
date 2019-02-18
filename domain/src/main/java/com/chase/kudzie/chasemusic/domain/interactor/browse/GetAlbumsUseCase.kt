package com.chase.kudzie.chasemusic.domain.interactor.browse

import com.chase.kudzie.chasemusic.domain.interactor.UseCase
import com.chase.kudzie.chasemusic.domain.model.Album
import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) : UseCase<List<Album>>() {
    override suspend fun executeOnBackground(): List<Album> {
        return albumRepository.getAlbums()
    }
}