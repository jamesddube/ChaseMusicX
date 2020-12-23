package com.chase.kudzie.chasemusic.domain.interactor.songs

import com.chase.kudzie.chasemusic.domain.model.MediaId
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.domain.repository.SongQueueRepository
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import java.lang.IllegalArgumentException
import javax.inject.Inject

class GetSongsByCategory @Inject constructor(
    private val songRepository: SongRepository,
    private val queueRepository: SongQueueRepository

) {
    suspend operator fun invoke(mediaIdCategory: MediaIdCategory): List<Song> {
        return when (mediaIdCategory.mediaId) {
            MediaId.ALBUMS -> songRepository.getSongsByAlbum(mediaIdCategory.idValue)
            MediaId.ARTISTS -> songRepository.getSongsByArtist(mediaIdCategory.idValue)
            MediaId.SONGS -> songRepository.getSongs()
            MediaId.PLAYLISTS -> songRepository.getSongsByPlaylist(mediaIdCategory.idValue)
            else -> throw IllegalArgumentException("Yeah we aren't throwing the song queue for now.")
        }
    }
}