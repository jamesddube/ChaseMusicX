package com.chase.kudzie.chasemusic.service.music

import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import com.chase.kudzie.chasemusic.service.music.extensions.toMediaItem
import com.chase.kudzie.chasemusic.service.music.repository.PlayerRepository
import com.chase.kudzie.chasemusic.service.music.repository.QueueRepository
import com.chase.kudzie.chasemusic.shared.injection.coroutinescope.DefaultScope
import kotlinx.coroutines.*
import javax.inject.Inject
import kotlin.coroutines.CoroutineContext


/**
 * @author Kudzai Chasinda
 */

class MediaSessionCallback @Inject constructor(
    private val player: PlayerRepository,
    private val queue: QueueRepository
) : MediaSessionCompat.Callback(), CoroutineScope by DefaultScope() {

    override fun onPrepare() {
        super.onPrepare()
        //TODO note:
        /*
        * I am thinking maybe on prepare I restore state from Shared Prefs and Db
        * This is called first and would be best to query the db and do the things
        * */
    }

    override fun onPlay() {
        super.onPlay()
        player.resume()
    }

    override fun onPause() {
        super.onPause()
        launch(Dispatchers.Main) {
            if (player.isPlaying())
                player.pause(true)
        }
    }

    override fun onSkipToNext() {
        super.onSkipToNext()
        launch {
            val song = queue.skipToNext()
            withContext(Dispatchers.Main) {
                if (song != null) {
                    player.play(song, false)
                } else {
                    //TODO maybe stop service at this point
                }

            }
        }
    }

    override fun onSkipToPrevious() {
        super.onSkipToPrevious()
        launch {
            val song = queue.skipToPrevious()
            withContext(Dispatchers.Main) {
                if (song != null) {
                    player.play(song, false)
                } else {
                    //TODO maybe stop service at this point
                }
            }
        }

    }

    override fun onStop() {
        super.onStop()
        launch {
            withContext(Dispatchers.Main) {
                //Release the player
                player.pause(false)
            }
        }
    }

    override fun onSeekTo(pos: Long) {
        super.onSeekTo(pos)
        launch {
            withContext(Dispatchers.Main) {
                player.seekTo(pos)
            }
        }
    }

    override fun onPlayFromMediaId(mediaId: String?, extras: Bundle?) {
        super.onPlayFromMediaId(mediaId, extras)
        launch {
            //Get the song from queue and play
            val song = queue.onPlayFromMediaId(mediaId!!)
            withContext(Dispatchers.Main) {
                if (song != null) {
                    player.play(song, false)
                } else {
                    //TODO maybe stop service at this point
                }
            }
        }
    }

    override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
        super.onPlayFromUri(uri, extras)
        TODO("implement")
    }


}