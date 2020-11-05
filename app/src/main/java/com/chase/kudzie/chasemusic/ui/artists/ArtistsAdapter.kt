package com.chase.kudzie.chasemusic.ui.artists

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.chase.kudzie.chasemusic.R
import com.chase.kudzie.chasemusic.databinding.ItemArtistBinding
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.model.ArtistDiff
import com.chase.kudzie.chasemusic.util.setGradientOnView
import kotlinx.android.synthetic.main.item_song.view.*

class ArtistsAdapter(
    val artistClick: (View, Artist) -> Unit,
    val context: Activity
) :
    ListAdapter<Artist, ArtistsAdapter.ItemHolder>(ArtistDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            ItemArtistBinding.inflate(
                LayoutInflater.from(parent.context),
                parent,
                false
            )
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val artist = getItem(position)

        holder.bind(artist)
    }


    inner class ItemHolder(private val binding: ItemArtistBinding) :
        RecyclerView.ViewHolder(binding.root) {

        fun bind(artist: Artist) {
            binding.run {
                this.artist = artist

                click(artist)
            }
        }

//        private fun simulatePlaceholder() {
//            //I will simulate a placeholder for now
//            val bitmapFromFactory =
//                BitmapFactory.decodeResource(context.resources, R.drawable.placeholder)
//            artistImage.setImageBitmap(bitmapFromFactory)
//            setGradientOnView(paletteView, bitmapFromFactory)
//        }

        fun click(artist: Artist) {
            itemView.setOnClickListener {
                artistClick(itemView, artist)
            }
        }
    }
}