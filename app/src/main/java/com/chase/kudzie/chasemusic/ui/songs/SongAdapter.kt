package com.chase.kudzie.chasemusic.ui.songs

import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chase.kudzie.chasemusic.R
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.model.SongDiff
import com.chase.kudzie.chasemusic.util.getAlbumArtUri

class SongAdapter(val onSongClick: (Song) -> Unit) :
    ListAdapter<Song, SongAdapter.ItemHolder>(SongDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context!!)
                .inflate(R.layout.item_song, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val song = getItem(position)
        holder.bind(song)

        holder.click(song)
    }


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvSongName: TextView = itemView.findViewById(R.id.song_name)
        private val tvArtistName: TextView = itemView.findViewById(R.id.artist_name)
        private val ivAlbumArt: ImageView = itemView.findViewById(R.id.album_artwork)


        fun bind(song: Song) {
            tvSongName.text = song.title
            tvArtistName.text = song.artistName
            Glide.with(itemView)
                .load(getAlbumArtUri(song.albumId))
                .placeholder(R.drawable.placeholder)
                .into(ivAlbumArt)
        }

        fun click(song: Song) {
            itemView.setOnClickListener {
                onSongClick(song)
            }
        }
    }


}