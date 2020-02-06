package com.chase.kudzie.chasemusic.media

interface IMediaProvider {
    fun playMediaFromId(mediaId: String)
    fun playPause()
}