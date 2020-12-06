package com.chase.kudzie.chasemusic.util.bindingadapters

import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.View
import android.widget.ImageButton
import android.widget.SeekBar
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
import com.chase.kudzie.chasemusic.util.setGradientOnView
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
