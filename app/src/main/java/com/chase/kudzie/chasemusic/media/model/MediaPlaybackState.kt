package com.chase.kudzie.chasemusic.media.model

import android.support.v4.media.session.PlaybackStateCompat

class MediaPlaybackState(playbackState: PlaybackStateCompat) {
    val isPlaying = playbackState.state == PlaybackStateCompat.STATE_PLAYING
    val isPaused = playbackState.state == PlaybackStateCompat.STATE_PAUSED

    val lastPositionUpdateTime = playbackState.lastPositionUpdateTime
    val playbackSpeed = playbackState.playbackSpeed

    val currentSeekPos = playbackState.position
}