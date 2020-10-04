package com.chase.kudzie.chasemusic.base

import androidx.appcompat.app.AppCompatActivity
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.media.IMediaProvider

abstract class BaseMediaActivity: AppCompatActivity(), IMediaProvider {
        
    override fun playMediaFromId(mediaIdCategory: MediaIdCategory) {
        TODO("Not yet implemented")
    }

    override fun playPause() {
        TODO("Not yet implemented")
    }
}