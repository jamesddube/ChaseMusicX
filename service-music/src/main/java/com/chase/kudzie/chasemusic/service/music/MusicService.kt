package com.chase.kudzie.chasemusic.service.music

import android.app.PendingIntent
import android.app.Service
import android.content.Intent
import android.os.Build
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.MediaBrowserCompat.MediaItem.FLAG_BROWSABLE
import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.core.content.ContextCompat
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import androidx.lifecycle.ServiceLifecycleDispatcher
import androidx.media.MediaBrowserServiceCompat
import com.chase.kudzie.chasemusic.domain.model.MediaId
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import com.chase.kudzie.chasemusic.domain.repository.ArtistRepository
import com.chase.kudzie.chasemusic.domain.repository.PlaylistRepository
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import com.chase.kudzie.chasemusic.service.music.data.MediaMetadata
import com.chase.kudzie.chasemusic.service.music.extensions.toChildMediaBrowserItem
import com.chase.kudzie.chasemusic.service.music.extensions.toMediaBrowserItem
import com.chase.kudzie.chasemusic.service.music.injection.inject
import com.chase.kudzie.chasemusic.service.music.notification.MediaNotificationManager
import com.chase.kudzie.chasemusic.service.music.repository.ServiceController
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.launch
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class MusicService : MediaBrowserServiceCompat(),
    LifecycleOwner,
    ServiceController,
    CoroutineScope by MainScope() {

    private var isServiceStarted = false
    private val dispatcher = ServiceLifecycleDispatcher(this)

    @Inject
    internal lateinit var callback: MediaSessionCallback

    @Inject
    internal lateinit var mediaSession: MediaSessionCompat

    @Inject
    internal lateinit var mediaNotification: MediaNotificationManager

    @Inject
    internal lateinit var metadata: MediaMetadata

    @Inject
    internal lateinit var songsRepository: SongRepository

    @Inject
    internal lateinit var albumRepository: AlbumRepository

    @Inject
    internal lateinit var playlistRepository: PlaylistRepository

    @Inject
    internal lateinit var artistRepository: ArtistRepository

    companion object {
        const val MEDIA_ID_ROOT = "ROOT"
    }


    override fun onStartCommand(intent: Intent?, flags: Int, startId: Int): Int {
        isServiceStarted = true

        return Service.START_NOT_STICKY
    }


    override fun onCreate() {
        super.onCreate()
        inject()
        setupMediaSession()
        lifecycle.run {
            addObserver(metadata)
            addObserver(mediaNotification)
        }
    }

    private fun setupMediaSession() {
        //Setup Media Session and retrieve a session token
        mediaSession.setMediaButtonReceiver(makeMediaButtonReceiver())
        mediaSession.setCallback(callback)
        callback.onPrepare()
        mediaSession.isActive = true

        sessionToken = mediaSession.sessionToken
    }

    override fun onDestroy() {
        dispatcher.onServicePreSuperOnDestroy()

        super.onDestroy()
        mediaSession.setMediaButtonReceiver(null)
        mediaSession.setCallback(null)
        mediaSession.isActive = false
        mediaSession.release()
    }

    override fun onGetRoot(
        clientPackageName: String,
        clientUid: Int,
        rootHints: Bundle?
    ): BrowserRoot? {
        if (clientPackageName == packageName) {
            return BrowserRoot(MEDIA_ID_ROOT, null)
        }
        return null
    }

    override fun onLoadChildren(
        parentId: String,
        result: Result<MutableList<MediaBrowserCompat.MediaItem>>
    ) {
        if (parentId == MEDIA_ID_ROOT) {
            result.sendResult(getMediaRoots())
            return
        }
        result.detach()

        launch(Dispatchers.Default) {
            //SongList here
            val mediaId = MediaId.values()
                .toList()
                .find { it.toString() == parentId }

            val songList: MutableList<MediaBrowserCompat.MediaItem> = mutableListOf()
            if (mediaId != null) {
                when (mediaId) {
                    MediaId.SONGS -> songList.addAll(
                        songsRepository.getSongs().map {
                            it.toMediaBrowserItem()
                        }
                    )

                    MediaId.ALBUMS -> songList.addAll(
                        albumRepository.getAlbums().map {
                            it.toMediaBrowserItem()
                        }
                    )

                    MediaId.ARTISTS -> songList.addAll(
                        artistRepository.getArtists().map { it.toMediaBrowserItem() })

                    MediaId.PLAYLISTS -> songList.addAll(
                        playlistRepository.getPlaylists().map {
                            it.toMediaBrowserItem()
                        }
                    )

                    else -> throw IllegalArgumentException("Like where are these Media IDs coming from mate? $mediaId")
                }
            } else {

                val mediaIdCategory = MediaIdCategory.fromString(parentId)
                val category = mediaIdCategory.mediaId
                val idValue = mediaIdCategory.idValue

                when (category) {
                    MediaId.ALBUMS -> songList.addAll(
                        songsRepository.getSongsByAlbum(idValue)
                            .map { it.toChildMediaBrowserItem(mediaIdCategory) }
                    )

                    MediaId.ARTISTS -> songList.addAll(
                        songsRepository.getSongsByArtist(idValue)
                            .map { it.toChildMediaBrowserItem(mediaIdCategory) }
                    )

                    MediaId.PLAYLISTS -> songList.addAll(
                        songsRepository.getSongsByPlaylist(idValue)
                            .map { it.toChildMediaBrowserItem(mediaIdCategory) }
                    )

                    else -> throw IllegalArgumentException("Invalid Category :::: $category")
                }
            }
            result.sendResult(songList)
        }
    }

    private fun getMediaRoots(): MutableList<MediaBrowserCompat.MediaItem> =
        mutableListOf(
            MediaBrowserCompat.MediaItem(
                MediaDescriptionCompat.Builder().apply {
                    setMediaId(MediaId.SONGS.toString())
                    setTitle("Songs")
                }.build(), FLAG_BROWSABLE
            ),
            MediaBrowserCompat.MediaItem(
                MediaDescriptionCompat.Builder().apply {
                    setMediaId(MediaId.ALBUMS.toString())
                    setTitle("Albums")
                }.build(), FLAG_BROWSABLE
            ),
            MediaBrowserCompat.MediaItem(
                MediaDescriptionCompat.Builder().apply {
                    setMediaId(MediaId.ARTISTS.toString())
                    setTitle("Artists")
                }.build(), FLAG_BROWSABLE
            ),
            MediaBrowserCompat.MediaItem(
                MediaDescriptionCompat.Builder().apply {
                    setMediaId(MediaId.PLAYLISTS.toString())
                    setTitle("Playlists")
                }.build(), FLAG_BROWSABLE
            )
        )

    private fun makeMediaButtonReceiver(): PendingIntent {
        val intent = Intent(Intent.ACTION_MEDIA_BUTTON)
        intent.setClass(this, this.javaClass)

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            return PendingIntent.getForegroundService(
                this,
                0,
                intent,
                PendingIntent.FLAG_CANCEL_CURRENT
            )
        }
        return PendingIntent.getService(this, 0, intent, PendingIntent.FLAG_CANCEL_CURRENT)
    }

    override fun getLifecycle(): Lifecycle {
        return dispatcher.lifecycle
    }

    override fun start() {
        if (!isServiceStarted) {
            val intent = Intent(this, MusicService::class.java)
            intent.action = "action.KEEP_SERVICE_ALIVE"
            ContextCompat.startForegroundService(this, intent)
            isServiceStarted = true
        }
    }

    override fun stop() {
        if (isServiceStarted) {
            isServiceStarted = false
            stopSelf()
        }
    }

    override fun notifySongEnded(isTrackEnded: Boolean) {
        callback.onSkipToNext() //TODO provide better implementation
    }


}

