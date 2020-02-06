package com.chase.kudzie.chasemusic.ui.songs

import com.chase.kudzie.chasemusic.domain.model.Song

interface OnSongClickListener {
    fun onSongClicked(song: Song)
}