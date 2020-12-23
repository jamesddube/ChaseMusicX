package com.chase.kudzie.chasemusic.service.music.repository

import com.chase.kudzie.chasemusic.service.music.model.PlayableMediaItem

/**
 * @author Kudzai Chasinda
 */
internal interface PlayerRepository : PlayerPlaybackState {

    fun isPlaying(): Boolean

    fun resume()

    fun play(mediaItem: PlayableMediaItem, hasTrackEnded: Boolean)

    fun pause(isServiceAlive: Boolean)

    fun seekTo(milliseconds: Long)

    fun likeTrack()

    fun prepare(mediaItem: PlayableMediaItem)

    fun getDuration(): Long
}