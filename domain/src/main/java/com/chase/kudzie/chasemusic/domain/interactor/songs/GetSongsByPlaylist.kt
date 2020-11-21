package com.chase.kudzie.chasemusic.domain.interactor.songs

import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import javax.inject.Inject

class GetSongsByPlaylist @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(playlistId: Long) = repository.getSongsByPlaylist(playlistId)
}