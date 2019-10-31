package com.chase.kudzie.chasemusic.domain.interactor.browse.playlists

import com.chase.kudzie.chasemusic.domain.repository.PlaylistRepository
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class GetPlaylists @Inject constructor(
    val repository: PlaylistRepository
) {
    suspend operator fun invoke() = repository.getPlaylists()
}