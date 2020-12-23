package com.chase.kudzie.chasemusic.images

import com.bumptech.glide.load.model.ModelLoader
import com.bumptech.glide.load.model.ModelLoaderFactory
import com.bumptech.glide.load.model.MultiModelLoaderFactory
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.domain.repository.DeezerRepository
import java.io.InputStream
import javax.inject.Inject

class ArtistModelLoaderFactory @Inject constructor(
    private val deezerRepository: DeezerRepository
) : ModelLoaderFactory<Artist, InputStream> {

    override fun build(multiFactory: MultiModelLoaderFactory): ModelLoader<Artist, InputStream> {
        return ArtistModelLoader(deezerRepository)
    }

    override fun teardown() {
    }

}