package com.chase.kudzie.chasemusic.domain.interactor.browse.album

import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import javax.inject.Inject

/**
 * @author Kudzai A Chasinda
 * */

class GetAlbums @Inject constructor(
    private val albumRepository: AlbumRepository
) {
    suspend operator fun invoke() = albumRepository.getAlbums()
}