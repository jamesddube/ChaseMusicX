package com.chase.kudzie.chasemusic.data.preferences

import android.content.Context
import android.content.SharedPreferences
import androidx.core.content.edit

object SharedPreferencesFactory {

    private const val CHASE_MUSIC_PREFERENCES = "chase_music_prefs"

    private const val CURRENT_POSITION_IN_QUEUE = "current_position_in_queue"
    private const val REPEAT_MODE = "repeat_mode"
    private const val PLAY_MODE = "play_mode"
    private const val SHUFFLE_MODE = "shuffle_mode"
    private const val CURRENT_SONG_POS = "song_duration_position"

    fun makeChaseMusicPreferences(context: Context): SharedPreferences {
        return context.getSharedPreferences(CHASE_MUSIC_PREFERENCES, Context.MODE_PRIVATE)
    }

    var SharedPreferences.currentPositionInQueue: Int
        get() = getInt(CURRENT_POSITION_IN_QUEUE, 0)
        set(value) {
            edit {
                putInt(CURRENT_POSITION_IN_QUEUE, value)
            }
        }

    var SharedPreferences.repeatMode: Int
        get() = getInt(REPEAT_MODE, 0)
        set(value) {
            edit {
                putInt(REPEAT_MODE, value)
            }
        }

    var SharedPreferences.playMode: Int
        get() = getInt(PLAY_MODE, 0)
        set(value) {
            edit {
                putInt(PLAY_MODE, value)
            }
        }

    var SharedPreferences.shuffleMode: Int
        get() = getInt(SHUFFLE_MODE, 0)
        set(value) {
            edit {
                putInt(SHUFFLE_MODE, value)
            }
        }

    var SharedPreferences.currentSongDurationPos: Long
        get() = getLong(CURRENT_SONG_POS, 0L)
        set(value) {
            edit {
                putLong(CURRENT_SONG_POS, value)
            }
        }
}