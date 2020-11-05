package com.chase.kudzie.chasemusic.ui.albumdetails.adapters

import android.view.LayoutInflater
import android.view.ViewGroup
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.chase.kudzie.chasemusic.databinding.ItemSongDetailBinding
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.model.SongDiff

class AlbumSongsAdapter(val onSongClick: (Song) -> Unit) :
    ListAdapter<Song, AlbumSongsAdapter.ItemHolder>(SongDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemSongDetailBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val song = getItem(position)
        holder.bind(song)
    }


    inner class ItemHolder(private val binding: ItemSongDetailBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(song: Song) {
            binding.run {
                this.song = song
                click(song)
            }
        }

        private fun click(song: Song) {
            itemView.setOnClickListener {
                onSongClick(song)
            }
        }
    }


}