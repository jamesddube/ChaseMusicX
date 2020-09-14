package com.chase.kudzie.chasemusic.ui.albums

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chase.kudzie.chasemusic.databinding.FragmentAlbumsBinding
import com.chase.kudzie.chasemusic.domain.model.Album
import com.chase.kudzie.chasemusic.injection.ViewModelFactory
import dagger.android.support.AndroidSupportInjection
import javax.inject.Inject

class AlbumsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: AlbumViewModel

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        val binding = FragmentAlbumsBinding.inflate(inflater, container, false)

        binding.apply {
            viewModel.albums.observe(
                viewLifecycleOwner, Observer { albums ->
                    run {
                        albumsGrid.apply {
                            adapter = AlbumAdapter(::onAlbumClicked).apply {
                                submitList(albums)
                            }
                        }
                    }
                }
            )
        }

        return binding.root
    }

    private fun initViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(AlbumViewModel::class.java)
    }

    private fun onAlbumClicked(album: Album) {
        //Todo navigate
    }
}