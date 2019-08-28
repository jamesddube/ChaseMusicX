package com.chase.kudzie.chasemusic.domain.interactor.songs

import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class GetSong @Inject constructor(
    private val repository: SongRepository
) {

    suspend operator fun invoke(id: Long) = repository.getSong(id)
}