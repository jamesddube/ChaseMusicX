package com.chase.kudzie.chasemusic.domain.model

import java.lang.IllegalArgumentException

enum class MediaId {
    ALBUMS,
    ARTISTS,
    SONGS,
    PLAYLISTS,
    SONG_QUEUE,
}

/**
 * Data class for our MediaIdCategories and their Long Values
 * */
data class MediaIdCategory(
    val mediaId: MediaId,
    val idValue: Long = -1,
    val songId: Long = -1
) {
    companion object {
        const val TOKEN_CATEGORY = "-"
        const val TOKEN_SONG_ID = ":"

        fun makeAlbumCategory(albumId: Long, songId: Long): MediaIdCategory {
            return MediaIdCategory(MediaId.ALBUMS, albumId, songId)
        }

        fun makeArtistCategory(artistId: Long, songId: Long): MediaIdCategory {
            return MediaIdCategory(MediaId.ARTISTS, artistId, songId)
        }

        fun makeSongsCategory(songId: Long): MediaIdCategory {
            return MediaIdCategory(MediaId.SONGS, songId, songId)
        }

        fun makePlaylistCategory(playlistId: Long, songId: Long): MediaIdCategory {
            return MediaIdCategory(MediaId.PLAYLISTS, playlistId, songId)
        }

        fun makeSongQueueCategory(songId: Long): MediaIdCategory {
            return MediaIdCategory(MediaId.SONG_QUEUE, songId, songId)
        }

        fun fromString(mediaIdCategory: String): MediaIdCategory {
            //TODO maybe improve later??
            val splitValue = mediaIdCategory.split(TOKEN_CATEGORY)

            if (splitValue.size < 3)
                throw IllegalArgumentException("Hmm What is this you try to convert from string")

            val mediaId = MediaId.valueOf(splitValue[0])
            val idValue = splitValue[1].toLong()
            val songIdValue = splitValue[2].toLong()

            return MediaIdCategory(mediaId, idValue, songIdValue)
        }
    }




    override fun toString(): String {
        return "$mediaId${TOKEN_CATEGORY}${idValue}${TOKEN_CATEGORY}${songId}"
    }
}