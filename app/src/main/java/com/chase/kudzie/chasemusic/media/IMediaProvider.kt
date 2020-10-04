package com.chase.kudzie.chasemusic.media

import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory

interface IMediaProvider {
    fun playMediaFromId(mediaIdCategory: MediaIdCategory)
    fun playPause()
}