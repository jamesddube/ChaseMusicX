package com.chase.kudzie.chasemusic.service.music.data

import android.content.ContentUris
import android.content.Context
import android.net.Uri
import android.support.v4.media.MediaMetadataCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.LifecycleOwner
import com.chase.kudzie.chasemusic.domain.model.MediaConstants.METADATA_KEY_ALBUM_ID
import com.chase.kudzie.chasemusic.domain.scope.ApplicationContext
import com.chase.kudzie.chasemusic.service.music.extensions.retrieveGlideBitmap
import com.chase.kudzie.chasemusic.service.music.injection.scope.PerService
import com.chase.kudzie.chasemusic.domain.model.MediaItem
import com.chase.kudzie.chasemusic.service.music.repository.PlayerPlaybackState
import com.chase.kudzie.chasemusic.shared.injection.coroutinescope.DefaultScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.cancel
import kotlinx.coroutines.launch
import kotlinx.coroutines.yield
import javax.inject.Inject


@PerService
internal class MediaMetadata @Inject constructor(
    @ApplicationContext private val context: Context,
    private val mediaSession: MediaSessionCompat,
    playerPlaybackState: PlayerPlaybackState
) : PlayerPlaybackState.Listener,
    DefaultLifecycleObserver,
    CoroutineScope by DefaultScope() {

    init {
        playerPlaybackState.addListener(this)
    }

    override fun onPrepare(mediaItem: MediaItem) {
        onMetadataChanged(mediaItem)
    }

    override fun onMetadataChanged(mediaItem: MediaItem) {
        updateMediaSessionMetadata(mediaItem)
    }

    private fun updateMediaSessionMetadata(entity: MediaItem) {
        launch {
            val metadataBuilder = MediaMetadataCompat.Builder()
            metadataBuilder
                .putString(MediaMetadataCompat.METADATA_KEY_MEDIA_ID, entity.mediaId.toString())
                .putString(MediaMetadataCompat.METADATA_KEY_ARTIST, entity.artist)
                .putString(MediaMetadataCompat.METADATA_KEY_ALBUM, entity.album)
                .putString(MediaMetadataCompat.METADATA_KEY_TITLE, entity.title)
                .putLong(MediaMetadataCompat.METADATA_KEY_DURATION, entity.duration)
                .putLong(METADATA_KEY_ALBUM_ID, entity.albumId)
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_TITLE, entity.title)
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_SUBTITLE, entity.artist)
                .putString(MediaMetadataCompat.METADATA_KEY_DISPLAY_DESCRIPTION, entity.album)
            yield()

            val albumArt = context.retrieveGlideBitmap(getAlbumArtUri(entity.albumId))
            yield() //We yield to allow the suspend and resume to complete before proceeding

            metadataBuilder.putBitmap(MediaMetadataCompat.METADATA_KEY_ALBUM_ART, albumArt)

            mediaSession.setMetadata(metadataBuilder.build())
        }
    }


    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        cancel() //Cancel any pending coroutines
    }

    //TODO perhaps make this into extension function
    private fun getAlbumArtUri(albumId: Long): Uri =
        ContentUris.withAppendedId(
            Uri.parse("content://media/external/audio/albumart"), albumId
        )

}