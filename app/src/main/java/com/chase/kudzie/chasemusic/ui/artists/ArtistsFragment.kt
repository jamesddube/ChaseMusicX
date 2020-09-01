package com.chase.kudzie.chasemusic.ui.artists

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chase.kudzie.chasemusic.databinding.FragmentArtistsBinding
import dagger.android.support.AndroidSupportInjection

class ArtistsFragment : Fragment() {

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentArtistsBinding.inflate(inflater, container, false)

        return binding.root
    }
}