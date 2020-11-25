package com.chase.kudzie.chasemusic.util

import android.annotation.SuppressLint
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageButton
import android.widget.ImageView
import android.widget.SeekBar
import android.widget.TextView
import androidx.annotation.DrawableRes
import androidx.core.content.ContextCompat
import androidx.databinding.BindingAdapter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.chase.kudzie.chasemusic.R
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.media.model.MediaRepeatMode
import com.chase.kudzie.chasemusic.media.model.MediaShuffleMode
import com.google.android.material.floatingactionbutton.FloatingActionButton

/**
 * File for handling all issues to do with binding.
 * TODO split by type maybe?
 * */


fun ImageButton.setImageDrawableExt(@DrawableRes drawable: Int) {
    this.setImageDrawable(this.context.getDrawable(drawable))
}

@BindingAdapter("cycle_repeat")
fun ImageButton.bindRepeatMode(mode: MediaRepeatMode?) {
    mode?.let {
        when (mode.getMode()) {
            MediaRepeatMode.Mode.ALL -> {
                this.setImageDrawableExt(R.drawable.ic_repeat)
                this.setColorFilter(
                    ContextCompat.getColor(context, R.color.colorPrimary),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
            MediaRepeatMode.Mode.ONE -> {
                this.setImageDrawableExt(R.drawable.ic_repeat_one)
               this.setColorFilter(
                    ContextCompat.getColor(context, R.color.colorPrimary),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
            else -> {
                this.setImageDrawableExt(R.drawable.ic_repeat)
                clearColorFilter()
            }
        }
    }
}

@BindingAdapter("cycle_shuffle")
fun ImageButton.bindShuffleMode(mode: MediaShuffleMode?) {
    mode?.let {
        when (mode.getMode()) {
            MediaShuffleMode.Mode.ALL -> {
                this.setImageDrawableExt(R.drawable.ic_shuffle)
                this.setColorFilter(
                    ContextCompat.getColor(context, R.color.colorPrimary),
                    android.graphics.PorterDuff.Mode.SRC_IN
                )
            }
            else -> {
                this.setImageDrawableExt(R.drawable.ic_shuffle)
                clearColorFilter()
            }
        }
    }
}

@BindingAdapter("duration_update")
fun TextView.bindDurationUpdate(currentSongPos: Long) {
    this.text = makeReadableDuration(currentSongPos)
}

@BindingAdapter("duration_text")
fun TextView.bindDurationText(duration: Long) {
    this.text = makeReadableDuration(duration)
}


@BindingAdapter("track_number")
fun TextView.bindTrackNumber(trackNum: Int) {
    if (trackNum == 0) {
        this.text = "-"
    } else {
        val trackText = trackNum.toString()
        var trackNumber = 0
        trackNumber = if (trackText.length == 4) {
            trackText.substring(1).toInt()
        } else {
            trackText.toInt()
        }
        this.text = "$trackNumber"
    }

}

@BindingAdapter("play_pause_icon")
fun FloatingActionButton.bindPlayPauseIcon(isPlaying: Boolean) {
    if (isPlaying) {
        this.setImageResource(R.drawable.ic_pause_48)
    } else {
        this.setImageResource(R.drawable.ic_play_48)
    }
}

@BindingAdapter("play_pause_btn")
fun ImageButton.bindPlayPauseIcon(isPlaying: Boolean) {
    if (isPlaying) {
        this.setImageResource(R.drawable.ic_pause_24)
    } else {
        this.setImageResource(R.drawable.ic_play_24)
    }
}

@BindingAdapter("seekbar_duration")
fun SeekBar.bindSeekBar(currentSongPos: Long) {
    val seekProgress = (currentSongPos / 1000).toInt()
    this.progress = seekProgress
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

@BindingAdapter("playlist_icon")
fun ImageView.bindPlaylistIcon(name: String) {
    Glide.with(this.context)
        .load("")
        .placeholder(R.drawable.ic_playlist)
        .into(this)
}


@SuppressLint("SetTextI18n")
@BindingAdapter("playlist_count")
fun TextView.setPlaylistCount(count: Int) {
    this.text = "$count songs"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("song_count")
fun TextView.setSongCount(count: Int) {
    this.text = if (count > 1) "$count songs" else "$count song"
}

@SuppressLint("SetTextI18n")
@BindingAdapter("album_count")
fun TextView.setAlbumCount(count: Int) {
    this.text = if (count > 1) "$count albums" else "$count album"
}


@BindingAdapter("artist_artwork", "artist_load_listener", requireAll = false)
fun ImageView.bindArtistArtwork(artist: Artist?, loadListener: GlideBitmapLoadListener?) {
    val request = Glide.with(this)
        .asBitmap()
        .load(artist)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .onlyRetrieveFromCache(true)
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

@BindingAdapter("palette_bg")
fun View.setGradient(artist: Artist) {
    Glide.with(this.context)
        .asBitmap()
        .load(artist)
        .diskCacheStrategy(DiskCacheStrategy.AUTOMATIC)
        .onlyRetrieveFromCache(true)
        .into(object : CustomTarget<Bitmap>() {
            override fun onResourceReady(
                resource: Bitmap,
                transition: Transition<in Bitmap>?
            ) {
                setGradientOnView(this@setGradient, resource)
            }

            override fun onLoadCleared(placeholder: Drawable?) {
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                Glide.with(this@setGradient.context)
                    .asBitmap()
                    .load(R.drawable.artists_placeholder)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            setGradientOnView(this@setGradient, resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                        }
                    })
            }
        })
}
