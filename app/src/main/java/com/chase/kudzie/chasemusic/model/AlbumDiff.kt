package com.chase.kudzie.chasemusic.model

import androidx.recyclerview.widget.DiffUtil
import com.chase.kudzie.chasemusic.domain.model.Album

object AlbumDiff : DiffUtil.ItemCallback<Album>() {
    override fun areItemsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Album, newItem: Album): Boolean {
        return oldItem == newItem
    }
}