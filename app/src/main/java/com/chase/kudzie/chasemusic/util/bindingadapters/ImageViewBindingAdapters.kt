package com.chase.kudzie.chasemusic.util.bindingadapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.widget.ImageView
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.chase.kudzie.chasemusic.R
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.util.GlideBitmapLoadListener
import com.chase.kudzie.chasemusic.util.GlideDrawableLoadListener
import com.chase.kudzie.chasemusic.util.getAlbumArtUri


@BindingAdapter("cover_uri", "placeholder", requireAll = false)
fun ImageView.bindImageIconUri(uri: Uri, placeholder: Drawable?) {
    val request = Glide.with(this)
        .load(uri)
    if (placeholder != null) request.placeholder(placeholder)
    request.into(this)
}

@BindingAdapter("playlist_icon")
fun ImageView.bindPlaylistIcon(name: String) {
    Glide.with(this.context)
        .load("")
        .placeholder(R.drawable.ic_playlist)
        .into(this)
}

@BindingAdapter("album_artwork", "placeholder", "load_listener", requireAll = false)
fun ImageView.bindAlbumArt(
    albumId: Long,
    placeholder: Drawable?,
    loadListener: GlideDrawableLoadListener?
) {
    val request = Glide.with(this)
        .load(getAlbumArtUri(albumId))
    if (placeholder != null) request.placeholder(placeholder)
    if (loadListener != null) request.listener(loadListener)
    request.into(this)
}

@BindingAdapter("artist_artwork", "artist_load_listener", requireAll = false)
fun ImageView.bindArtistArtwork(artist: Artist?, loadListener: GlideBitmapLoadListener?) {
    val request = Glide.with(this)
        .asBitmap()
        .load(artist)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
    if (loadListener != null) request.listener(loadListener)

    request.into(object : CustomTarget<Bitmap>() {
        override fun onResourceReady(
            resource: Bitmap,
            transition: Transition<in Bitmap>?
        ) {
            this@bindArtistArtwork.setImageBitmap(resource)
        }

        override fun onLoadCleared(placeholder: Drawable?) {
        }

        override fun onLoadFailed(errorDrawable: Drawable?) {
            Glide.with(this@bindArtistArtwork.context)
                .asBitmap()
                .load(R.drawable.artists_placeholder)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        this@bindArtistArtwork.setImageBitmap(resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }
                })
        }
    })
}