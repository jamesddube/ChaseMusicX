package com.chase.kudzie.chasemusic.domain.test.factory

import com.chase.kudzie.chasemusic.domain.model.Artist

/**
 * Factory class for mock Artist instances
 */
object ArtistFactory : GeneratorFactory<Artist> {
    override fun generateObject(): Artist {
        return Artist(
            id = DataFactory.randomLong(),
            name = DataFactory.randomString(),
            albumCount = DataFactory.randomInt(),
            songCount = DataFactory.randomInt()
        )
    }

    override fun generateList(count: Int): List<Artist> {
        val artists = mutableListOf<Artist>()
        repeat(count) {
            artists.add(generateObject())
        }
        return artists
    }

}