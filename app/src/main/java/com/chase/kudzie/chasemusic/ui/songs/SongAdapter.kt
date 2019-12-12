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

class SongAdapter : ListAdapter<Song, SongAdapter.ItemHolder>(SongDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context!!)
                .inflate(R.layout.item_song, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        holder.bind(getItem(position))
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
                .into(ivAlbumArt)
        }
    }


}