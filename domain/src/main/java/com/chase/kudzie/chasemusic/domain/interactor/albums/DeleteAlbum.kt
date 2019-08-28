package com.chase.kudzie.chasemusic.domain.interactor.albums

import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class DeleteAlbum @Inject constructor(
    private val repository: AlbumRepository
) {
    suspend operator fun invoke(params: Params?) {
        if (params == null) throw IllegalArgumentException("Param id cannot be null")
        return repository.deleteAlbum(params.id)
    }

    data class Params(val id: Long) {
        companion object {
            fun forAlbum(id: Long): Params = Params(id)
        }
    }
}