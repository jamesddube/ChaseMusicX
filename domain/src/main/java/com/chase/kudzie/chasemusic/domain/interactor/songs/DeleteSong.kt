package com.chase.kudzie.chasemusic.domain.interactor.songs

import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class DeleteSong @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(params: Params?) {
        if (params == null) throw IllegalArgumentException("Param id cannot be null")
        return repository.deleteSong(params.id)
    }

    data class Params(val id: Long) {
        companion object {
            fun forSong(id: Long): Params = Params(id)
        }
    }
}