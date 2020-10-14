package com.chase.kudzie.chasemusic.ui.playlistdetails

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

class PlaylistDetailsFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
}