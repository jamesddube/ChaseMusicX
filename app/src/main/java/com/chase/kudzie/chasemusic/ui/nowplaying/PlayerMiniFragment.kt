package com.chase.kudzie.chasemusic.ui.nowplaying

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import com.chase.kudzie.chasemusic.databinding.FragmentPlayerMiniBinding
import com.chase.kudzie.chasemusic.media.IMediaProvider
import com.chase.kudzie.chasemusic.media.model.MediaMetadata
import com.chase.kudzie.chasemusic.media.model.MediaPlaybackState
import dagger.android.support.AndroidSupportInjection

class PlayerMiniFragment : Fragment() {

    private lateinit var mediaProvider: IMediaProvider

    private var _binding: FragmentPlayerMiniBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        mediaProvider = this.activity as IMediaProvider
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayerMiniBinding.inflate(inflater, container, false)
        return binding.root
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            mediaProvider.observeMetadata().observe(
                viewLifecycleOwner, {
                    it?.let {
                        updateViewMetadata(it)
                    }
                }
            )

            mediaProvider.observePlaybackState().observe(
                viewLifecycleOwner, {
                    it?.let {
                        updatePlaybackState(it)
                    }
                }
            )

            skipToNextBtn.setOnClickListener {
                mediaProvider.skipToNext()
            }

            playPauseBtn.setOnClickListener {
                mediaProvider.playPause()
            }

            songName.isSelected = true

        }
    }

    private fun updatePlaybackState(playbackState: MediaPlaybackState) {
        this.binding.playbackState = playbackState

    }

    private fun updateViewMetadata(metadata: MediaMetadata) {
        this.binding.metadata = metadata
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}