package com.kudziechase.chasemusic.service.music.data

import com.kudziechase.chasemusic.service.music.model.MediaItem

interface MediaMetadataListener {
    fun onMetadataChanged(mediaItem: MediaItem)
}