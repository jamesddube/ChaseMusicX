package com.chase.kudzie.chasemusic.ui.playlists

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chase.kudzie.chasemusic.databinding.FragmentPlaylistsBinding

class PlaylistsFragment : Fragment() {

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlaylistsBinding.inflate(inflater, container, false)

        return binding.root
    }
}