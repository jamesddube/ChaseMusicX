package com.chase.kudzie.chasemusic.service.music.data

import android.content.ContentUris
import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.lifecycle.DefaultLifecycleObserver
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.chase.kudzie.chasemusic.domain.scope.ApplicationContext
import com.chase.kudzie.chasemusic.service.music.injection.scope.PerService
import com.chase.kudzie.chasemusic.service.music.model.MediaItem
import javax.inject.Inject


@PerService
class MediaMetadata @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mediaSession: MediaSessionCompat
) : MediaMetadataListener,
    DefaultLifecycleObserver {

    override fun onMetadataChanged(mediaItem: MediaItem) {
        updateMediaSessionMetadata(mediaItem)
    }

    private fun updateMediaSessionMetadata(entity: MediaItem) {
        val metadataBuilder = MediaMetadataCompat.Builder()

        metadataBuilder.putString(MediaMetadataCompat.METADATA_KEY_ARTIST, entity.artist)
            .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, entity.album)
            .putString(MediaMetadataCompat.METADATA_KEY_TITLE, entity.title)
            .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, entity.duration)

        metadataBuilder.putString(
            MediaMetadataCompat.METADATA_KEY_ALBUM_ART_URI,
            getAlbumArtUri(entity.albumId).toString()
        )

        mediaSession.setMetadata(metadataBuilder.build())


//        Glide.with(context)
//            .asBitmap()
//            .load()
//            .into(object : CustomTarget<Bitmap>() {
//                override fun onLoadCleared(placeholder: Drawable?) {
//                }
//
//                override fun onResourceReady(
//                    resource: Bitmap,
//                    transition: Transition<in Bitmap>?
//                ) {
//                    metadataBuilder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, resource)
//                    mediaSession.setMetadata(metadataBuilder.build())
//                }
//            })


    }

    private fun getAlbumArtUri(albumId: Long): Uri =
        ContentUris.withAppendedId(
            Uri.parse("content://media/external/audio/albumart"), albumId
        )
}