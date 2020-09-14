package com.chase.kudzie.chasemusic.model

import androidx.recyclerview.widget.DiffUtil
import com.chase.kudzie.chasemusic.domain.model.Playlist

object PlaylistDiff : DiffUtil.ItemCallback<Playlist>() {
    override fun areItemsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
        return oldItem.id == newItem.id
    }

    override fun areContentsTheSame(oldItem: Playlist, newItem: Playlist): Boolean {
       return oldItem == newItem
    }
}