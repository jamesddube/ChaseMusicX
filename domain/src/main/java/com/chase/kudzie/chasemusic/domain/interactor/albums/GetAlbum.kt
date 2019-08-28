package com.chase.kudzie.chasemusic.domain.interactor.albums

import com.chase.kudzie.chasemusic.domain.model.Album
import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class GetAlbum @Inject constructor(
    private val repository: AlbumRepository
) {
    suspend operator fun invoke(params: Params?): Album {
        if (params == null) throw IllegalArgumentException("id for album cannot be null")
        return repository.getAlbum(params.id)
    }

    data class Params(val id: Long) {
        companion object {
            fun forAlbum(id: Long): Params = Params(id)
        }
    }
}