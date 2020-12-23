package com.chase.kudzie.chasemusic.service.music.data

import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat.*
import android.util.Log
import com.chase.kudzie.chasemusic.domain.repository.PreferencesRepository
import com.chase.kudzie.chasemusic.service.music.injection.scope.PerService
import javax.inject.Inject

@PerService
internal class ShuffleMode @Inject constructor(
    private val repository: PreferencesRepository,
    private val mediaSession: MediaSessionCompat
) {

    private var mode = SHUFFLE_MODE_INVALID
        set(value) {
            field = value
            mediaSession.setShuffleMode(value)
            repository.setShuffleMode(value)
        }

    init {
        this.mode = repository.getRepeatMode()
    }

    fun isShuffleModeOn(): Boolean = mode != SHUFFLE_MODE_NONE

    fun toggleShuffleMode(): Boolean {
        Log.e("SHUFFLE","toggle")
        val currentMode = mode

        mode = if (currentMode != SHUFFLE_MODE_NONE) {
            SHUFFLE_MODE_NONE
        } else {
            SHUFFLE_MODE_ALL
        }

        return mode != SHUFFLE_MODE_NONE
    }

}