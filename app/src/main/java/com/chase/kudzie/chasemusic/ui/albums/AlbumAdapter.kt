package com.chase.kudzie.chasemusic.ui.albums

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chase.kudzie.chasemusic.R
import com.chase.kudzie.chasemusic.databinding.ItemAlbumBinding
import com.chase.kudzie.chasemusic.domain.model.Album
import com.chase.kudzie.chasemusic.model.AlbumDiff
import com.chase.kudzie.chasemusic.util.getAlbumArtUri

class AlbumAdapter(val albumClicked: (Album) -> Unit) :
    ListAdapter<Album, AlbumAdapter.ItemHolder>(AlbumDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemAlbumBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val album = getItem(position)

        holder.bind(album)
    }


    inner class ItemHolder(private val binding: ItemAlbumBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(album: Album) {
            binding.run {
                this.album = album

                click(album)
            }
        }

        private fun click(album: Album) {
            itemView.setOnClickListener {
                albumClicked(album)
            }
        }
    }
}