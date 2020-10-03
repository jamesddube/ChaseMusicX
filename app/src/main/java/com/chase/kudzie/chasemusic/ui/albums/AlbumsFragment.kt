package com.chase.kudzie.chasemusic.ui.albums

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import com.chase.kudzie.chasemusic.databinding.FragmentAlbumsBinding
import com.chase.kudzie.chasemusic.domain.model.Album
import com.chase.kudzie.chasemusic.injection.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class AlbumsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val viewModel: AlbumViewModel by viewModels {
        viewModelFactory
    }

    private var _binding: FragmentAlbumsBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentAlbumsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {
            viewModel.albums.observe(viewLifecycleOwner, { albums ->
                albumsGrid.apply {
                    adapter = AlbumAdapter(::onAlbumClicked).apply {
                        submitList(albums)
                    }
                }
            })
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onAlbumClicked(album: Album) {
        //Todo navigate
    }
}