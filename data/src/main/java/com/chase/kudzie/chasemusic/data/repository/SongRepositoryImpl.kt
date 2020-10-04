package com.chase.kudzie.chasemusic.data.repository

import android.content.ContentResolver
import android.content.Context
import com.chase.kudzie.chasemusic.data.extensions.queryAll
import com.chase.kudzie.chasemusic.data.extensions.queryOne
import com.chase.kudzie.chasemusic.data.loaders.AlbumLoader
import com.chase.kudzie.chasemusic.data.loaders.ArtistLoader
import com.chase.kudzie.chasemusic.data.loaders.SongLoader
import com.chase.kudzie.chasemusic.data.mapper.toSong
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import com.chase.kudzie.chasemusic.domain.scope.ApplicationContext
import javax.inject.Inject

class SongRepositoryImpl @Inject constructor(
    @ApplicationContext context: Context
) : SongRepository {
    private val contentResolver: ContentResolver = context.contentResolver

    override suspend fun getSongs(): List<Song> {
        return contentResolver.queryAll(
            SongLoader(contentResolver).getAll()
        ) { it.toSong() }
    }

    override suspend fun findSongs(searchString: String): List<Song> {
        return contentResolver.queryAll(
            SongLoader(contentResolver).findSongs(searchString)
        ) { it.toSong() }
    }

    override suspend fun getSong(id: Long): Song {
        return contentResolver.queryOne(
            SongLoader(contentResolver).getSong(id)
        ) { it.toSong() }!!
    }

    override suspend fun deleteSong(id: Long) {
        TODO("research song deletion via content provider?")
    }

    override suspend fun getSongsByAlbum(albumId: Long): List<Song> {
        return contentResolver.queryAll(
            AlbumLoader(contentResolver).getAlbumSongs(albumId)
        ) { it.toSong() }
    }

    override suspend fun getSongsByArtist(artistId: Long): List<Song> {
        return contentResolver.queryAll(
            ArtistLoader(contentResolver).getArtistSongs(artistId)
        ) { it.toSong() }
    }

    override suspend fun getSongsByPlaylist(playlistId: Long): List<Song> {
        TODO("Not yet implemented")
    }
}