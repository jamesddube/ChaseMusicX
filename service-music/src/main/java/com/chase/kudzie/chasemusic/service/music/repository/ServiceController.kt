package com.chase.kudzie.chasemusic.service.music.repository

internal interface ServiceController {
    fun start()
    fun stop()

    fun notifySongEnded(isTrackEnded:Boolean) //TODO provide better implementation
}