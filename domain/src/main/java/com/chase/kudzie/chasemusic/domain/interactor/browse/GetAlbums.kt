package com.chase.kudzie.chasemusic.domain.interactor.browse

import com.chase.kudzie.chasemusic.domain.interactor.CoroutineUseCase
import com.chase.kudzie.chasemusic.domain.model.Album
import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import javax.inject.Inject

class GetAlbumsUseCase @Inject constructor(
    private val albumRepository: AlbumRepository
) : CoroutineUseCase<List<Album>, Nothing>() {
    override suspend fun executeOnBackground(params: Nothing?): List<Album> {
        return albumRepository.getAlbums()
    }
}