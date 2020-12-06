package com.chase.kudzie.chasemusic.ui.queue

import android.os.Bundle
import android.view.View
import androidx.lifecycle.asLiveData
import com.chase.kudzie.chasemusic.R
import com.chase.kudzie.chasemusic.base.BaseMediaActivity
import com.chase.kudzie.chasemusic.databinding.ActivityPlayingQueueBinding
import com.chase.kudzie.chasemusic.media.model.PlayableMediaItem
import com.chase.kudzie.chasemusic.util.contentView
import dagger.android.AndroidInjection
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.flow.flowOn

class PlayingQueueActivity : BaseMediaActivity() {

    private val binding: ActivityPlayingQueueBinding by contentView(R.layout.activity_playing_queue)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding.apply {
            observePlayingQueue()
                .flowOn(Dispatchers.Default)
                .asLiveData().observe(this@PlayingQueueActivity, {
                    songsList.apply {
                        adapter = PlayingQueueAdapter(::onQueueItemClicked).apply {
                            submitList(it)
                        }
                    }
                })

            toolbar.setNavigationOnClickListener {
                finish()
            }
        }
    }

    private fun onQueueItemClicked(view: View, item: PlayableMediaItem) {

    }

}