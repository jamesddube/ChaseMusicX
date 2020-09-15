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
        holder.bind(artist)
        holder.click(artist)
    }


    inner class ItemHolder(itemView: View) : RecyclerView.ViewHolder(itemView) {
        private val paletteView: View = itemView.findViewById(R.id.palette_view);
        private val artistImage: ImageView = itemView.findViewById(R.id.artist_image)
        private val artistName: TextView = itemView.findViewById(R.id.artist_name)


        fun bind(artist: Artist) {
            artistName.text = artist.name

            simulatePlaceholder()

            loadArtistImageFromNetwork(artist)
        }

        private fun loadArtistImageFromNetwork(artist: Artist) {
            //TODO Maybe make into some type of extension?
            Glide.with(artistImage.context)
                .asBitmap()
                .load(artist)
                .into(object : CustomTarget<Bitmap>() {
                    override fun onResourceReady(
                        resource: Bitmap,
                        transition: Transition<in Bitmap>?
                    ) {
                        artistImage.setImageBitmap(resource)
                        setGradientOnView(paletteView, resource)
                    }

                    override fun onLoadCleared(placeholder: Drawable?) {
                    }

                    override fun onLoadFailed(errorDrawable: Drawable?) {
                        Glide.with(artistImage.context)
                            .asBitmap()
                            .load(R.drawable.placeholder)
                            .into(object : CustomTarget<Bitmap>() {
                                override fun onResourceReady(
                                    resource: Bitmap,
                                    transition: Transition<in Bitmap>?
                                ) {
                                    artistImage.setImageBitmap(resource)
                                    setGradientOnView(paletteView, resource)
                                }

                                override fun onLoadCleared(placeholder: Drawable?) {
                                }
                            })
                    }
                })
        }

        private fun simulatePlaceholder() {
            //I will simulate a placeholder for now
            val bitmapFromFactory =
                BitmapFactory.decodeResource(context.resources, R.drawable.placeholder)
            artistImage.setImageBitmap(bitmapFromFactory)
            setGradientOnView(paletteView, bitmapFromFactory)
        }

        fun click(artist: Artist) {
            itemView.setOnClickListener {
                artistClick(artist)
            }
        }
    }
}