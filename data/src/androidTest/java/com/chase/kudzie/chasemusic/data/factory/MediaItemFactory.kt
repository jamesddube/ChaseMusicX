package com.chase.kudzie.chasemusic.data.factory

import com.chase.kudzie.chasemusic.domain.model.Song

object MediaItemFactory {

    fun makeFakeSongs(): List<Song> {
        return listOf(
            Song(
                id = 1,
                title = "Emotionless",
                artistName = "Drake",
                albumName = "Scorpion",
                artistId = 3,
                trackNumber = 12,
                duration = 2000L,
                positionInQueue = 1,
                albumId = 2
            ),
            Song(
                id = 2,
                title = "Intentions",
                artistName = "Justin Beiber",
                albumName = "Single",
                artistId = 4,
                trackNumber = 1,
                duration = 3500L,
                positionInQueue = 2,
                albumId = 5
            ),
            Song(
                id = 3,
                title = "Unless it Hurts",
                artistName = "Galantis",
                albumName = "Church",
                artistId = 8,
                trackNumber = 4,
                duration = 2550L,
                positionInQueue = 3,
                albumId = 9
            ),
            Song(
                id = 4,
                title = "Perfect - Robin Schulz Mix",
                artistName = "Ed Sheeran",
                albumName = "Divide",
                artistId = 7,
                trackNumber = 11,
                duration = 3400L,
                positionInQueue = 4,
                albumId = 6
            ),
            Song(
                id = 6,
                title = "Light it Up",
                artistName = "Naya",
                albumName = "Major Lazer Essentials",
                artistId = 6,
                trackNumber = 10,
                duration = 4000L,
                positionInQueue = 5,
                albumId = 4
            )
        )
    }

    fun makeFakeAlbum1() {

    }

    fun makeFakeAlbum2() {

    }
}