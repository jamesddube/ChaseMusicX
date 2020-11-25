package com.chase.kudzie.chasemusic.service.music.data

import android.support.v4.media.session.MediaSessionCompat
import android.support.v4.media.session.PlaybackStateCompat.*
import android.util.Log
import com.chase.kudzie.chasemusic.domain.repository.PreferencesRepository
import com.chase.kudzie.chasemusic.service.music.injection.scope.PerService
import javax.inject.Inject

@PerService
internal class RepeatMode @Inject constructor(
    private val repository: PreferencesRepository,
    private val mediaSession: MediaSessionCompat
) {

    private var mode = REPEAT_MODE_INVALID
        set(value) {
            field = value
            mediaSession.setRepeatMode(value)
            repository.setRepeatMode(value)
        }

    init {
        this.mode = repository.getRepeatMode()
    }

    fun isRepeatModeAll(): Boolean = this.mode == REPEAT_MODE_ALL
    fun isRepeatModeOne(): Boolean = this.mode == REPEAT_MODE_ONE


    fun toggleRepeatMode() {
        Log.e("REPEAT","toggle repeat")
        var currentMode = mode

        mode = when (currentMode) {
            REPEAT_MODE_NONE -> REPEAT_MODE_ALL
            REPEAT_MODE_ALL -> REPEAT_MODE_ONE
            else -> REPEAT_MODE_NONE
        }
    }
}