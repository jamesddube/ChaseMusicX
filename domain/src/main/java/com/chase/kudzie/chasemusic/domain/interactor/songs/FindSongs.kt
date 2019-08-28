package com.chase.kudzie.chasemusic.domain.interactor.songs

import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class FindSongs @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(params: Params?): List<Song> {
        if (params == null) throw IllegalArgumentException("Param searchString cannot be null")
        return repository.findSongs(params.searchString)
    }

    data class Params(val searchString: String) {
        companion object {
            fun forSong(searchString: String): Params = Params(searchString)
        }
    }
}