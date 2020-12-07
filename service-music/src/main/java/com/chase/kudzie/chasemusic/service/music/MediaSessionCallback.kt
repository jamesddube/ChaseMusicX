package com.chase.kudzie.chasemusic.service.music

import android.net.Uri
import android.os.Bundle
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.service.music.data.RepeatMode
import com.chase.kudzie.chasemusic.service.music.data.ShuffleMode
import com.chase.kudzie.chasemusic.service.music.repository.PlayerRepository
import com.chase.kudzie.chasemusic.service.music.repository.QueueRepository
import com.chase.kudzie.chasemusic.shared.injection.coroutinescope.DefaultScope
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject


/**
 * @author Kudzai Chasinda
 */

internal class MediaSessionCallback @Inject constructor(
    private val player: PlayerRepository,
    private val queue: QueueRepository,
    private val repeatMode: RepeatMode,
    private val shuffleMode: ShuffleMode
) : MediaSessionCompat.Callback(),
    CoroutineScope by DefaultScope() {

    override fun onPrepare() {
        launch(Dispatchers.Main) {
            val song = queue.prepare()
            if (song != null) {
                player.prepare(song)
            }
        }
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
        onSkipToNext(true)
    }

    private fun onSkipToNext(fromUser: Boolean) = launch(Dispatchers.Main) {
        val nextSong = queue.skipToNext(fromUser)
        if (nextSong != null) {
            player.play(nextSong, false)
        } else {
            val currentSong = queue.getCurrentPlayingSong()
            if (currentSong != null) {
                player.play(currentSong, true)
                player.pause(false)
                player.seekTo(0L)
            } else {
                onPause()
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

    override fun onPlayFromMediaId(mediaId: String, extras: Bundle?) {
        super.onPlayFromMediaId(mediaId, extras)
        Log.e("MEDIA_ID", mediaId)
        launch {
            //Get the song from queue and play
            val song = queue.onPlayFromMediaId(MediaIdCategory.fromString(mediaId))
            withContext(Dispatchers.Main) {
                if (song != null) {
                    player.play(song, false)
                } else {

                }
            }
        }
    }

    override fun onPlayFromUri(uri: Uri?, extras: Bundle?) {
        super.onPlayFromUri(uri, extras)
        TODO("implement")
    }

    override fun onSetRepeatMode(repeatMode: Int) {
        super.onSetRepeatMode(repeatMode)

        this.repeatMode.toggleRepeatMode()
    }

    override fun onSetShuffleMode(shuffleMode: Int) {
        super.onSetShuffleMode(shuffleMode)

        val isShuffleOn = this.shuffleMode.toggleShuffleMode()
        if (isShuffleOn) {
            queue.shuffleSongs()
        } else {
            queue.sortSongs()
        }
    }

    fun playerHasRequestedNext(isTrackEnded: Boolean) {
        //I do not know if this will even work bro
        onSkipToNext(false)
    }


}