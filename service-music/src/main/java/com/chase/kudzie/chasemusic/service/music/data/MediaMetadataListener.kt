package com.chase.kudzie.chasemusic.service.music.data

import com.chase.kudzie.chasemusic.service.music.model.MediaItem

interface MediaMetadataListener {
    fun onMetadataChanged(mediaItem: MediaItem)
}