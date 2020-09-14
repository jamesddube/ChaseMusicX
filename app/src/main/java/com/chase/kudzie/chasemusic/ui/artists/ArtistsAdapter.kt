package com.chase.kudzie.chasemusic.ui.artists

import android.app.Activity
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import androidx.core.content.ContextCompat
import androidx.palette.graphics.Palette
import androidx.recyclerview.widget.ListAdapter
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
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
        //Manually change for now
        holder.bind(artist, true)
        holder.click(artist)
    }


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val paletteView: View = itemView.findViewById(R.id.palette_view);
        private val artistImage: ImageView = itemView.findViewById(R.id.artist_image)
        private val artistName: TextView = itemView.findViewById(R.id.artist_name)


        fun bind(artist: Artist, isGradient: Boolean) {
            artistName.text = artist.name

            Glide.with(itemView)
                .asBitmap()
                .load(artist)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        artistImage.setImageBitmap(resource)
                        if (isGradient) {
                            setGradientOnView(paletteView, resource)
                        } else {
                            //Setup Palette on Palette view
                            Palette.from(resource).generate { palette ->
                                paletteView.setBackgroundColor(
                                    palette?.vibrantSwatch?.rgb ?: ContextCompat.getColor(
                                        context, R.color.colorBackgroundFallback
                                    )
                                )
                            }
                        }
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {

                    }
                })
        }


        fun click(artist: Artist) {
            itemView.setOnClickListener {
                artistClick(artist)
            }
        }
    }
}