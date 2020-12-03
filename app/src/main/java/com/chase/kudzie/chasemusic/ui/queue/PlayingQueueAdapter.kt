package com.chase.kudzie.chasemusic.ui.queue

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chase.kudzie.chasemusic.databinding.ItemQueueSongBinding
import com.chase.kudzie.chasemusic.media.model.PlayableMediaItem
import com.chase.kudzie.chasemusic.model.PlayableMediaItemDiff

class PlayingQueueAdapter(
    val itemClick: (View, PlayableMediaItem) -> Unit
) : ListAdapter<PlayableMediaItem, PlayingQueueAdapter.ItemHolder>(PlayableMediaItemDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemQueueSongBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val item = getItem(position)

        holder.bind(item)
    }


    inner class ItemHolder(private val binding: ItemQueueSongBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(item: PlayableMediaItem) {
            binding.run {
                this.item = item

                click(item)
                executePendingBindings()
            }
        }

        private fun click(item: PlayableMediaItem) {
            itemView.setOnClickListener { view ->
                itemClick(view, item)
            }
        }
    }
}