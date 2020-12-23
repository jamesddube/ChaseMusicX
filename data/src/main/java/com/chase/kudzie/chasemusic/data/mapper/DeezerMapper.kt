package com.chase.kudzie.chasemusic.data.mapper

import com.chase.kudzie.chasemusic.data.model.DeezerArtistEntity
import com.chase.kudzie.chasemusic.data.model.DeezerDataEntity
import com.chase.kudzie.chasemusic.domain.model.DeezerArtist
import com.chase.kudzie.chasemusic.domain.model.DeezerData
import javax.inject.Inject

class DeezerMapper @Inject constructor() : ResponseMapper<DeezerArtistEntity, DeezerArtist> {
    override fun mapToDomain(entity: DeezerArtistEntity): DeezerArtist {
        return DeezerArtist(
            data = entity.data.map { mapDataToDomain(it) },
            total = entity.total
        )
    }

    private fun mapDataToDomain(entity: DeezerDataEntity): DeezerData {
        return DeezerData(
            id = entity.id,
            name = entity.name,
            link = entity.link,
            picture = entity.picture,
            pictureSmall = entity.pictureSmall,
            pictureMedium = entity.pictureMedium,
            pictureBig = entity.pictureBig,
            pictureXL = entity.pictureXL,
            type = entity.type
        )
    }
}