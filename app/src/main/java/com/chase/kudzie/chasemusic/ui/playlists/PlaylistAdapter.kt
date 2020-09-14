package com.chase.kudzie.chasemusic.ui.playlists

import android.annotation.SuppressLint
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chase.kudzie.chasemusic.R
import com.chase.kudzie.chasemusic.domain.model.Playlist
import com.chase.kudzie.chasemusic.model.PlaylistDiff

class PlaylistAdapter(val onPlaylistClick: (Playlist) -> Unit) :
    ListAdapter<Playlist, PlaylistAdapter.ItemHolder>(PlaylistDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context!!)
                .inflate(R.layout.item_playlist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val playlist = getItem(position)

        holder.bind(playlist)

        holder.click(playlist)
    }

    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvPlaylistName: TextView = itemView.findViewById(R.id.playlist_name)
        private val tvSongCount: TextView = itemView.findViewById(R.id.song_count_label)
        private val ivPlaylistIcon: ImageView = itemView.findViewById(R.id.playlist_icon)


        @SuppressLint("SetTextI18n")
        fun bind(playlist: Playlist) {
            tvPlaylistName.text = playlist.name
            tvSongCount.text = "${playlist.songCount} songs"

            Glide.with(itemView)
                .load("")
                .placeholder(R.drawable.ic_playlist)
                .into(ivPlaylistIcon)
        }

        fun click(playlist: Playlist) {
            itemView.setOnClickListener {
                onPlaylistClick(playlist)
            }
        }
    }
}