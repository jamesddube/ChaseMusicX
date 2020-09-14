package com.chase.kudzie.chasemusic.ui.artists

import android.app.Activity
import android.graphics.BitmapFactory
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.FrameLayout
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.chase.kudzie.chasemusic.R
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.model.ArtistDiff
import com.chase.kudzie.chasemusic.util.setGradientOnView

class ArtistsAdapter(
    val artistClick: (Artist) -> Unit,
    val context: Activity
) :
    ListAdapter<Artist, ArtistsAdapter.ItemHolder>(ArtistDiff) {

    override fun onCreateViewHolder(parent: ViewGroup, viewType: Int): ItemHolder {
        return ItemHolder(
            LayoutInflater.from(parent.context!!)
                .inflate(R.layout.item_artist, parent, false)
        )
    }

    override fun onBindViewHolder(holder: ItemHolder, position: Int) {
        val artist = getItem(position)

        holder.bind(artist)
        holder.click(artist)
    }


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val gradientView: FrameLayout = itemView.findViewById(R.id.gradient_view)
        private val artistImage: ImageView = itemView.findViewById(R.id.artist_image)
        private val artistName: TextView = itemView.findViewById(R.id.artist_name)

        private val imageList = intArrayOf(
            R.drawable.riri,
            R.drawable.wale,
            R.drawable.eminem,
        )

        private fun randomImage(): Int {
            return imageList.random()
        }

        fun bind(artist: Artist) {
            artistName.text = artist.name
            val bitmap = BitmapFactory.decodeResource(context.resources, randomImage())
            Glide.with(itemView)
                .load(bitmap)
                .circleCrop()
                .into(artistImage)
            setGradientOnView(gradientView, bitmap)
        }


        fun click(artist: Artist) {
            itemView.setOnClickListener {
                artistClick(artist)
            }
        }
    }
}