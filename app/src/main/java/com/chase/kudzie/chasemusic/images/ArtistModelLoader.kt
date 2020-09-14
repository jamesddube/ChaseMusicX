package com.chase.kudzie.chasemusic.images

import com.bumptech.glide.load.Options
import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.signature.ObjectKey
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.domain.repository.DeezerRepository
import java.io.InputStream

class ArtistModelLoader(
    private val deezerRepository: DeezerRepository
) : ModelLoader<Artist, InputStream> {
    override fun buildLoadData(
        model: Artist,
        width: Int,
        height: Int,
        options: Options
    ): ModelLoader.LoadData<InputStream>? {
        return ModelLoader.LoadData(
            ObjectKey(model),
            ArtistDataFetcher(
                model,
                deezerRepository
            )
        )
    }

    override fun handles(model: Artist): Boolean {
        return true
    }

}