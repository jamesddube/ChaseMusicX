package com.chase.kudzie.chasemusic.images

import android.content.Context
import com.bumptech.glide.Glide
import com.bumptech.glide.Registry
import com.bumptech.glide.annotation.GlideModule
import com.bumptech.glide.module.AppGlideModule
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.injection.component.inject
import java.io.InputStream
import javax.inject.Inject

@GlideModule
class ChaseMusicGlideModule : AppGlideModule() {

    @Inject
    lateinit var artistModelLoaderFactory: ArtistModelLoaderFactory

    override fun registerComponents(context: Context, glide: Glide, registry: Registry) {
        inject(context)

        registry.prepend(Artist::class.java, InputStream::class.java, artistModelLoaderFactory)
    }
}