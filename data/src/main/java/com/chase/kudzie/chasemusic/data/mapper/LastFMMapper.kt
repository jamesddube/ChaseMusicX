package com.chase.kudzie.chasemusic.data.mapper

import com.chase.kudzie.chasemusic.data.model.*
import com.chase.kudzie.chasemusic.domain.model.*
import javax.inject.Inject

class LastFMMapper @Inject constructor() : ResponseMapper<LastFMArtistEntity, LastFMArtist> {
    override fun mapToDomain(entity: LastFMArtistEntity): LastFMArtist {
        return LastFMArtist(
            artist = mapArtistEntityToDomain(entity.artist)
        )
    }

    private fun mapArtistEntityToDomain(entity: LFMArtistEntity): LFMArtist {
        return LFMArtist(
            name = entity.name,
            image = entity.image.map { mapImageEntityToDomain(it) },
            stats = mapStatsToDomain(entity.stats),
            bio = mapBioToDomain(entity.bio)
        )
    }

    private fun mapImageEntityToDomain(entity: LFMImageEntity): LFMImage {
        return LFMImage(
            text = entity.text,
            size = entity.size
        )
    }

    private fun mapStatsToDomain(entity: StatsEntity): Stats {
        return Stats(
            listeners = entity.listeners,
            playCount = entity.playCount
        )
    }

    private fun mapBioToDomain(entity: BioEntity): Bio {
        return Bio(
            summary = entity.summary,
            content = entity.content
        )
    }
}