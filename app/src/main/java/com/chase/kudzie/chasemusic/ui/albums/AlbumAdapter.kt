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
import com.chase.kudzie.chasemusic.domain.model.Album
import com.chase.kudzie.chasemusic.model.AlbumDiff
import com.chase.kudzie.chasemusic.util.getAlbumArtUri

class AlbumAdapter(val albumClicked: (Album) -> Unit) :
    ListAdapter<Album, AlbumAdapter.ItemHolder>(AlbumDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context!!)
                .inflate(R.layout.item_album, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val album = getItem(position)
        holder.bind(album)

        holder.click(album)
    }


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val tvAlbumName: TextView = itemView.findViewById(R.id.album_name)
        private val tvArtistName: TextView = itemView.findViewById(R.id.artist_name)
        private val ivAlbumArtwork: ImageView = itemView.findViewById(R.id.album_artwork)

        fun bind(album: Album) {
            tvAlbumName.text = album.title
            tvArtistName.text = album.artistName

            Glide.with(itemView)
                .load(getAlbumArtUri(album.id))
                .into(ivAlbumArtwork)
        }

        fun click(album: Album) {
            itemView.setOnClickListener {
                albumClicked(album)
            }
        }
    }
}