package com.chase.kudzie.chasemusic.model

import androidx.recyclerview.widget.DiffUtil
import com.chase.kudzie.chasemusic.media.model.PlayableMediaItem

object PlayableMediaItemDiff : DiffUtil.ItemCallback<PlayableMediaItem>() {

    override fun areItemsTheSame(oldItem: PlayableMediaItem, newItem: PlayableMediaItem)
            : Boolean = oldItem.positionInQueue == newItem.positionInQueue


    override fun areContentsTheSame(
        oldItem: PlayableMediaItem,
        newItem: PlayableMediaItem
    ): Boolean = oldItem == newItem

}