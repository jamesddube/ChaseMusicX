package com.chase.kudzie.chasemusic.model

import androidx.recyclerview.widget.DiffUtil
import com.chase.kudzie.chasemusic.domain.model.Artist

object ArtistDiff : DiffUtil.ItemCallback<Artist>() {
    override fun areItemsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Artist, newItem: Artist): Boolean {
        return oldItem == newItem
    }
}