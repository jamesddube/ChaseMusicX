package com.chase.kudzie.chasemusic.ui.artists

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.ImageView
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProviders
import com.chase.kudzie.chasemusic.databinding.FragmentArtistsBinding
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.extensions.retrieveGlideBitmap
import com.chase.kudzie.chasemusic.injection.ViewModelFactory
import com.chase.kudzie.chasemusic.util.setGradientOnView
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.*
import javax.inject.Inject

class ArtistsFragment : Fragment(),
    CoroutineScope by MainScope() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: ArtistsViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        initViewModels()
    }

    private fun initViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory)
            .get(ArtistsViewModel::class.java)
    }

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

        binding.apply {
            viewModel.artists.observe(
                viewLifecycleOwner, { artists ->
                    run {
                        artistsGrid.apply {
                            adapter = ArtistsAdapter(
                                ::onArtistClick,
                                requireActivity()
                            ).apply {
                                submitList(artists)
                            }
                        }
                    }
                }
            )
        }


        return binding.root
    }

    private fun onArtistClick(artist: Artist) {
        //Todo handle click
    }

    private fun onExtractBitmapAndApply(artist: Artist, background: View) {
        launch(Dispatchers.Main) {
            val bitmap = requireContext().retrieveGlideBitmap("")
            yield()
            bitmap?.let {
                setGradientOnView(background, bitmap)
            }
        }
    }
}