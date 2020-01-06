package com.chase.kudzie.chasemusic.service.music.repository

import com.chase.kudzie.chasemusic.service.music.model.MediaItem

/**
 * @author Kudzai Chasinda
 */
interface PlayerRepository {

    fun isPlaying(): Boolean

    fun resume()

    fun play(mediaItem: MediaItem, hasTrackEnded: Boolean)

    fun pause(isServiceAlive: Boolean)

    fun seekTo(milliseconds: Long)

    fun likeTrack()

    fun prepare(mediaItem: MediaItem)

    fun getDuration(): Long
}