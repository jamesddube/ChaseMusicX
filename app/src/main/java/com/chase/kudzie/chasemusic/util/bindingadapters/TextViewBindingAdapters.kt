package com.chase.kudzie.chasemusic.util.bindingadapters

import android.annotation.SuppressLint
import android.widget.TextView
import androidx.databinding.BindingAdapter
import com.chase.kudzie.chasemusic.util.makeReadableDuration


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