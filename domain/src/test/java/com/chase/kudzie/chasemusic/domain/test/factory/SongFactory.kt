package com.chase.kudzie.chasemusic.domain.test.factory

import com.chase.kudzie.chasemusic.domain.model.Song

/**
 * Factory class for mock Song instances
 */
object SongFactory : GeneratorFactory<Song> {
    override fun generateObject(): Song {
        return Song(
            id = DataFactory.randomLong(),
            artistName = DataFactory.randomString(),
            title = DataFactory.randomString(),
            albumName = DataFactory.randomString(),
            artistId = DataFactory.randomLong(),
            trackNumber = DataFactory.randomInt(),
            duration = DataFactory.randomLong(),
            albumId = DataFactory.randomLong(),
            positionInQueue = -1
        )
    }

    override fun generateList(count: Int): List<Song> {
        val songs = mutableListOf<Song>()
        repeat(count) {
            songs.add(generateObject())
        }
        return songs
    }

}