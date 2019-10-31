package com.chase.kudzie.chasemusic.domain.interactor.browse.song

import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 * */
class GetSongs @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke() = repository.getSongs()
}