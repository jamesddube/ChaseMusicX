package com.chase.kudzie.chasemusic.mapper

import com.chase.kudzie.chasemusic.domain.model.DeezerArtist
import com.chase.kudzie.chasemusic.domain.model.DeezerData
import com.chase.kudzie.chasemusic.model.DeezerArtistView
import com.chase.kudzie.chasemusic.model.DeezerDataView
import javax.inject.Inject

class DeezerMapper @Inject constructor() : Mapper<DeezerArtistView, DeezerArtist> {
    override fun mapToView(type: DeezerArtist): DeezerArtistView {
        return DeezerArtistView(
            data = type.data.map { mapDataToView(it) },
            total = type.total
        )
    }

    private fun mapDataToView(type: DeezerData): DeezerDataView {
        return DeezerDataView(
            id = type.id,
            name = type.name,
            link = type.link,
            picture = type.picture,
            pictureSmall = type.pictureSmall,
            pictureMedium = type.pictureMedium,
            pictureBig = type.pictureBig,
            pictureXL = type.pictureXL,
            type = type.type
        )
    }
}