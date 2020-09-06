package com.chase.kudzie.chasemusic.domain.test.factory

import com.chase.kudzie.chasemusic.domain.model.Album

/**
 * Factory class for mock Album instances
 */
object AlbumFactory : GeneratorFactory<Album> {
    override fun generateObject(): Album {
        return Album(
            id = DataFactory.randomLong(),
            artistId = DataFactory.randomLong(),
            artistName = DataFactory.randomString(),
            year = DataFactory.randomInt(),
            songCount = DataFactory.randomInt(),
            title = DataFactory.randomString()
        )
    }

    override fun generateList(count: Int): List<Album> {
        val albums = mutableListOf<Album>()
        repeat(count) {
            albums.add(generateObject())
        }
        return albums
    }


}
