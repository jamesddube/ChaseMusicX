package com.chase.kudzie.chasemusic.model

import androidx.recyclerview.widget.DiffUtil
import com.chase.kudzie.chasemusic.domain.model.Song

object SongDiff : DiffUtil.ItemCallback<Song>() {
    override fun areItemsTheSame(oldItem: Song, newItem: Song)
            : Boolean = oldItem.id == newItem.id

    override fun areContentsTheSame(oldItem: Song, newItem: Song)
            : Boolean = oldItem == newItem
}