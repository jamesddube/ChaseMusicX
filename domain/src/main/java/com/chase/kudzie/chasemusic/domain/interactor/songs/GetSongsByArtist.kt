package com.chase.kudzie.chasemusic.domain.interactor.songs

import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import javax.inject.Inject

class GetSongsByArtist @Inject constructor(
    private val repository: SongRepository
) {
    suspend operator fun invoke(artistId: Long) = repository.getSongsByArtist(artistId)
}