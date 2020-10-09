package com.chase.kudzie.chasemusic.ui.nowplaying

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel

class PlayerViewModel : ViewModel() {

    var duration: Long = 0L
    //set the duration

    val _durationLiveData = MutableLiveData<Long>()
    val durationLiveData: LiveData<Long> = _durationLiveData




}