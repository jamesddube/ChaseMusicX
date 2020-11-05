package com.chase.kudzie.chasemusic.ui.nowplaying

import android.content.Context
import android.os.Bundle
import android.os.SystemClock
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import android.widget.SeekBar
import androidx.fragment.app.Fragment
import com.chase.kudzie.chasemusic.databinding.FragmentPlayerBinding
import com.chase.kudzie.chasemusic.media.IMediaProvider
import com.chase.kudzie.chasemusic.media.model.MediaMetadata
import com.chase.kudzie.chasemusic.media.model.MediaPlaybackState
import com.chase.kudzie.chasemusic.util.makeReadableDuration
import dagger.android.support.AndroidSupportInjection
import kotlinx.coroutines.*
import timber.log.Timber
import java.util.*

class PlayerFragment : Fragment(), CoroutineScope by MainScope() {

    private lateinit var mediaProvider: IMediaProvider

    private var _binding: FragmentPlayerBinding? = null
    private val binding get() = _binding!!

    private var durationJob: Job? = null

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
        _binding = FragmentPlayerBinding.inflate(inflater, container, false)
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

            skipToPrevBtn.setOnClickListener {
                mediaProvider.skipToPrevious()
            }

            playPauseBtn.setOnClickListener {
                mediaProvider.playPause()
            }

            songName.isSelected = true


            musicSeekbar.setOnSeekBarChangeListener(object : SeekBar.OnSeekBarChangeListener {
                override fun onProgressChanged(
                    seekBar: SeekBar,
                    progress: Int,
                    fromUser: Boolean
                ) {
                    startTime.text = makeReadableDuration(progress.toLong() * 1000)
                }

                override fun onStartTrackingTouch(seekBar: SeekBar?) {
                    stopSeekbarUpdate()
                }

                override fun onStopTrackingTouch(seekBar: SeekBar) {
                    mediaProvider.seekTo(seekBar.progress.toLong() * 1000)

                }
            })
        }
    }

    private fun updatePlaybackState(playbackState: MediaPlaybackState) {
        this.binding.playbackState = playbackState
        if (playbackState.isPlaying) {
            startSeekBarUpdate(playbackState)
        } else {
            stopSeekbarUpdate()
        }
    }

    private fun updateViewMetadata(metadata: MediaMetadata) {
        this.binding.metadata = metadata
        this.binding.musicSeekbar.max = (metadata.duration / 1000).toInt()
    }

    private fun stopSeekbarUpdate() {
        durationJob?.cancel()
    }

    private fun startSeekBarUpdate(playbackState: MediaPlaybackState) {
        stopSeekbarUpdate()
        durationJob = CoroutineScope(Dispatchers.IO).launchPeriodicAsync(1000) {
            updateProgress(playbackState)
        }
    }

    private fun updateProgress(playbackState: MediaPlaybackState) {
        var currentPosition = playbackState.currentSeekPos
        if (playbackState.isPlaying) {
            val timeDelta = SystemClock.elapsedRealtime() - playbackState.lastPositionUpdateTime
            currentPosition += (timeDelta * playbackState.playbackSpeed).toInt()

        }
        this.binding.musicSeekbar.progress = (currentPosition / 1000).toInt()
    }

    override fun onDestroy() {
        super.onDestroy()
        durationJob?.cancel()
        _binding = null
    }

    private fun CoroutineScope.launchPeriodicAsync(
        repeatMillis: Long,
        action: () -> Unit
    ) = this.async {
        if (repeatMillis > 0) {
            while (true) {
                action()
                delay(repeatMillis)
            }
        } else {
            action()
        }
    }
}