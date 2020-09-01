package com.chase.kudzie.chasemusic.ui.albumsongs

import android.content.Context
import androidx.fragment.app.Fragment
import dagger.android.support.AndroidSupportInjection

class AlbumSongsFragment : Fragment() {


    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }
}