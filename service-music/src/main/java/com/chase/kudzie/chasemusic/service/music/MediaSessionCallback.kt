package com.chase.kudzie.chasemusic.service.music

import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import com.chase.kudzie.chasemusic.service.music.extensions.toMediaItem
import com.chase.kudzie.chasemusic.service.music.repository.PlayerRepository
import com.chase.kudzie.chasemusic.shared.injection.coroutinescope.DefaultScope
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/**
 * @author Kudzai Chasinda
 */

class MediaSessionCallback @Inject constructor(
    private val player: PlayerRepository,
    private val songRepository: SongRepository
) : MediaSessionCompat.Callback(), CoroutineScope by DefaultScope() {

    override fun onPrepare() {
        super.onPrepare()
    }

    override fun onPlay() {
        super.onPlay()
        player.resume()
    }

    override fun onPause() {
        super.onPause()
        launch(Dispatchers.Main) {
            if (player.isPlaying())
                player.pause(false)
        }
    }

    override fun onSkipToNext() {
        super.onSkipToNext()
        TODO("implement")
    }

    override fun onSkipToPrevious() {
        super.onSkipToPrevious()
        TODO("implement")

    }

    override fun onStop() {
        super.onStop()
        TODO("implement")

    }

    override fun onSeekTo(pos: Long) {
        super.onSeekTo(pos)
        TODO("implement")

    }

    override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
        super.onPlayFromMediaId(mediaId, extras)
        launch {
            val song = songRepository.getSong(mediaId!!.toLong())
            withContext(Dispatchers.Main) {
                player.play(song.toMediaItem(), false)
            }
        }
    }

    override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
        super.onPlayFromUri(uri, extras)
        TODO("implement")
    }


}