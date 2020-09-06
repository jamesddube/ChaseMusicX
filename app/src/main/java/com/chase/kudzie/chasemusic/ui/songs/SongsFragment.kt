package com.chase.kudzie.chasemusic.ui.songs

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chase.kudzie.chasemusic.databinding.FragmentSongsBinding
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.injection.ViewModelFactory
import com.chase.kudzie.chasemusic.media.IMediaProvider
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class SongsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private lateinit var mediaProvider: IMediaProvider

    private lateinit var viewModel: SongViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
        mediaProvider = this.activity as IMediaProvider
    }

    private fun initViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(SongViewModel::class.java)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentSongsBinding.inflate(inflater, container, false)

        binding.apply {
            viewModel.songs.observe(
                viewLifecycleOwner, Observer { songs ->
                    run {
                        songsList.apply {
                            adapter = SongAdapter(::onSongClicked).apply {
                                submitList(songs)
                            }
                        }
                    }
                }
            )
        }

        return binding.root
    }

    private fun onSongClicked(song: Song) {
        mediaProvider.playMediaFromId(song.id.toString())
    }
}