package com.chase.kudzie.chasemusic.ui.queue

import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.fragment.app.Fragment
import androidx.lifecycle.asLiveData
import androidx.lifecycle.lifecycleScope
import androidx.navigation.fragment.findNavController
import com.chase.kudzie.chasemusic.databinding.FragmentPlayingQueueBinding
import com.chase.kudzie.chasemusic.media.IMediaProvider
import com.chase.kudzie.chasemusic.media.model.PlayableMediaItem
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.collect
import kotlinx.coroutines.flow.flowOn
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.launch

class PlayingQueueFragment : Fragment() {

    private lateinit var mediaProvider: IMediaProvider

    private var _binding: FragmentPlayingQueueBinding? = null
    private val binding get() = _binding!!

    override fun onAttach(context: Context) {
        super.onAttach(context)
        AndroidSupportInjection.inject(this)
        mediaProvider = requireActivity() as IMediaProvider
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
    }

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        _binding = FragmentPlayingQueueBinding.inflate(inflater, container, false)
        return binding.root
    }

    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        binding.apply {

            mediaProvider.observePlayingQueue()
                .flowOn(Dispatchers.Default)
                .asLiveData().observe(viewLifecycleOwner, {
                    songsList.apply {
                        adapter = PlayingQueueAdapter(::onQueueItemClicked).apply {
                            submitList(it)
                        }
                    }
                })

            toolbar.setNavigationOnClickListener {
                findNavController().navigateUp()
            }
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        _binding = null
    }

    private fun onQueueItemClicked(view: View, item: PlayableMediaItem) {

    }

}