package com.chase.kudzie.chasemusic.ui.artistdetails

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.view.animation.AnimationUtils
import androidx.core.view.doOnPreDraw
import androidx.fragment.app.Fragment
import androidx.fragment.app.viewModels
import androidx.navigation.findNavController
import androidx.navigation.fragment.FragmentNavigatorExtras
import androidx.navigation.fragment.findNavController
import androidx.navigation.fragment.navArgs
import com.chase.kudzie.chasemusic.R
import com.chase.kudzie.chasemusic.databinding.FragmentArtistDetailsBinding
import com.chase.kudzie.chasemusic.domain.model.Album
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.extensions.themeColor
import com.chase.kudzie.chasemusic.injection.ViewModelFactory
import com.chase.kudzie.chasemusic.media.IMediaProvider
import com.chase.kudzie.chasemusic.ui.albumdetails.adapters.DetailSongsAdapter
import com.chase.kudzie.chasemusic.ui.artistdetails.adapters.DetailAlbumsAdapter
import com.chase.kudzie.chasemusic.ui.artists.ArtistsViewModel
import com.chase.kudzie.chasemusic.util.artistLoadListener
import com.google.android.material.transition.MaterialContainerTransform
import com.google.android.material.transition.MaterialElevationScale
import dagger.android.support.AndroidSupportInjection
import java.util.concurrent.TimeUnit
import javax.inject.Inject

class ArtistDetailsFragment : Fragment() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    private val artistDetailsViewModel: ArtistDetailsViewModel by viewModels {
        viewModelFactory
    }

    private val artistViewModel: ArtistsViewModel by viewModels {
        viewModelFactory
    }

    private val args: ArtistDetailsFragmentArgs by navArgs()

    private lateinit var mediaProvider: IMediaProvider

    private var _binding: FragmentArtistDetailsBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        mediaProvider = this.activity as IMediaProvider
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

        val artistId = args.artistId
        artistViewModel.getArtist(artistId)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentArtistDetailsBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            artistViewModel.artist.observe(viewLifecycleOwner, { artist ->
                artistLoadListener {}
                this.artist = artist
                //Fetch our data
                artistDetailsViewModel.getAlbumsByArtist(artist.id)
                artistDetailsViewModel.getSongsByArtist(artist.id)

            })

            artistDetailsViewModel.albums.observe(viewLifecycleOwner, { albums ->
                albumList.apply {
                    adapter = DetailAlbumsAdapter(::onAlbumClick).apply {
                        submitList(albums)
                    }
                }

                this.albumSize = albums.size
            })

            artistDetailsViewModel.songs.observe(viewLifecycleOwner, { songs ->
                songList.apply {
                    adapter = DetailSongsAdapter(::onSongClick).apply {
                        submitList(songs)
                    }
                }

                this.songSize = songs.size
            })

            btnShuffle.setOnClickListener {
                //TODO implement and execute shuffle
            }

            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }

            postponeEnterTransition(1000L, TimeUnit.MILLISECONDS)
            val interp = AnimationUtils.loadInterpolator(
                context,
                android.R.interpolator.fast_out_slow_in
            )

            sharedElementEnterTransition = MaterialContainerTransform().apply {
                duration = resources.getInteger(R.integer.motion_duration_large).toLong()
                interpolator = interp
                scrimColor = Color.TRANSPARENT
                setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
            }

            sharedElementReturnTransition = MaterialContainerTransform().apply {
                duration = 300L
                interpolator = interp
                scrimColor = Color.TRANSPARENT
                setAllContainerColors(requireContext().themeColor(R.attr.colorSurface))
            }

            view.doOnPreDraw { startPostponedEnterTransition() }

        }
    }

    private fun onAlbumClick(view: View, album: Album) {

        exitTransition = MaterialElevationScale(false).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }

        reenterTransition = MaterialElevationScale(true).apply {
            duration = resources.getInteger(R.integer.motion_duration_large).toLong()
        }

        val extras = FragmentNavigatorExtras(
            view to "album_shared_element"
        )
        val directions = ArtistDetailsFragmentDirections.actionArtistDetailsToAlbumDetails(album.id)
        view.findNavController().navigate(directions, extras)
    }

    private fun onSongClick(song: Song) {
        mediaProvider.playMediaFromId(MediaIdCategory.makeArtistCategory(args.artistId, song.id))
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }
}