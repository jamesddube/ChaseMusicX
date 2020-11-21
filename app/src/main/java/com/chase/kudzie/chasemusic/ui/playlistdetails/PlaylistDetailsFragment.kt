package com.chase.kudzie.chasemusic.ui.playlistdetails

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chase.kudzie.chasemusic.databinding.FragmentPlaylistDetailsBinding
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.injection.ViewModelFactory
import com.chase.kudzie.chasemusic.media.IMediaProvider
import com.chase.kudzie.chasemusic.ui.songs.SongAdapter
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class PlaylistDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val playlistDetailsViewModel: PlaylistDetailsViewModel by viewModels {
        viewModelFactory
    }

    private var _binding: FragmentPlaylistDetailsBinding? = null
    private val binding get() = _binding!!

    private lateinit var mediaProvider: IMediaProvider

    private val args: PlaylistDetailsFragmentArgs by navArgs()

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        mediaProvider = this.activity as IMediaProvider
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val playlistId = args.playlistId
        playlistDetailsViewModel.getSongsByPlaylist(playlistId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlaylistDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            playlistDetailsViewModel.songs.observe(viewLifecycleOwner, { songs ->
                songsList.apply {
                    adapter = SongAdapter(::onSongClicked).apply {
                        submitList(songs)
                    }
                }
            })

            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            toolbar.title = args.playlistTitle
        }
    }

    private fun onSongClicked(song: Song) {
        mediaProvider.playMediaFromId(
            MediaIdCategory.makePlaylistCategory(
                args.playlistId,
                song.id
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}