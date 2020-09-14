package com.chase.kudzie.chasemusic.ui.playlists

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.chase.kudzie.chasemusic.databinding.FragmentPlaylistsBinding
import com.chase.kudzie.chasemusic.domain.model.Playlist
import com.chase.kudzie.chasemusic.injection.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PlaylistsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: PlaylistsViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
    }

    private fun initViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(PlaylistsViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentPlaylistsBinding.inflate(inflater, container, false)

        binding.apply {
            viewModel.playlists.observe(
                viewLifecycleOwner, { playlists ->
                    run {
                        playlistList.apply {
                            adapter = PlaylistAdapter(::onPlaylistClick).apply {
                                submitList(playlists)
                            }
                        }
                    }
                }
            )
        }

        return binding.root
    }

    private fun onPlaylistClick(playlist: Playlist) {
        //Todo handle click
    }
}