package com.chase.kudzie.chasemusic.service.music.repository

import android.support.v4.media.MediaDescriptionCompat
import android.support.v4.media.session.MediaSessionCompat
import android.util.Log
import androidx.lifecycle.DefaultLifecycleObserver
import androidx.lifecycle.Lifecycle
import androidx.lifecycle.LifecycleOwner
import com.chase.kudzie.chasemusic.domain.interactor.songs.GetSongsByCategory
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.domain.model.MediaItem
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.domain.repository.PreferencesRepository
import com.chase.kudzie.chasemusic.domain.repository.SongQueueRepository
import com.chase.kudzie.chasemusic.service.music.extensions.toMediaItem
import com.chase.kudzie.chasemusic.service.music.extensions.toPlayableMediaItem
import com.chase.kudzie.chasemusic.service.music.injection.scope.LifecycleService
import com.chase.kudzie.chasemusic.service.music.model.PlayableMediaItem
import com.chase.kudzie.chasemusic.shared.injection.coroutinescope.DefaultScope
import kotlinx.coroutines.*
import kotlinx.coroutines.flow.MutableSharedFlow
import kotlinx.coroutines.flow.collect
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

internal class QueueRepositoryImpl @Inject constructor(
    private val songsQueueRepository: SongQueueRepository,
    private val getSongsByCategory: GetSongsByCategory,
    private val preferencesRepository: PreferencesRepository,
    private val mediaSession: MediaSessionCompat,
    @LifecycleService lifecycle: Lifecycle
) : QueueRepository,
    CoroutineScope by DefaultScope(),
    DefaultLifecycleObserver {

    private val songQueue = ArrayList<MediaItem>()
    private var currentQueuePosition: Int = -1

    private var queueStateJob: Job? = null

    private val queueFlow = MutableSharedFlow<List<MediaItem>>(replay = 0)

    init {
        lifecycle.addObserver(this)
        launch {
            queueFlow.collect {
                consumeQueue(it)
            }
        }
    }

    private fun publishQueue(list: List<MediaItem>) {
        launch {
            queueFlow.emit(list)
        }
    }

    private fun consumeQueue(list: List<MediaItem>) {
        val queue = list.map { it.toQueueItem() }
        mediaSession.setQueue(
            queue
        )
    }

    override fun isQueueEmpty(): Boolean {
        return songQueue.isEmpty()
    }

    override fun onSetRepeatMode() {
        Log.d("QUEUEREP", " On Set Repeat Called Do something important here")
    }

    override suspend fun skipToNext(): PlayableMediaItem? {
        if (isQueueEmpty()) {
            return null
        }
        updatePositionInQueue(isNext = true)
        //TODO Maybe check if position in queue is valid

        val resultTrack = songQueue[currentQueuePosition]
        return resultTrack.toPlayableMediaItem()
    }

    override suspend fun skipToPrevious(): PlayableMediaItem? {
        if (isQueueEmpty())
            return null

        updatePositionInQueue(isNext = false)
        //TODO Maybe check if position in queue is valid

        val resultTrack = songQueue[currentQueuePosition]
        return resultTrack.toPlayableMediaItem()
    }

    override suspend fun prepare(): PlayableMediaItem? {
        val songsQueue = retrieveQueueState()
        val lastPositionInQueue = preferencesRepository.getCurrentQueuePosition()

        val currentTrackIndex =
            songsQueue.indexOfFirst { song -> song.positionInQueue == lastPositionInQueue }

        val resultTrack = songsQueue.getOrNull(currentTrackIndex) ?: return null

        publishQueue(songsQueue)
        updateSongQueue(songsQueue)

        currentQueuePosition = currentTrackIndex

        return resultTrack.toPlayableMediaItem()
    }

    private fun updateSongQueue(songs: List<MediaItem>) {
        songQueue.clear()
        songQueue.addAll(songs)
    }

    override suspend fun onPlayFromMediaId(mediaIdCategory: MediaIdCategory): PlayableMediaItem? {
        //The assumption is that we get all our songs without handling album stuff for now
        //Get all the songs and index them,
        //get the one song with this particular ID
        //update the new songs list with a position in queue
        val songId = mediaIdCategory.songId

        val allSongs = getSongsByCategory(mediaIdCategory)

        val queueSongs = allSongs.mapIndexed { index: Int, song: Song ->
            song.toIndexedSong(index).toMediaItem(mediaIdCategory)
        }

        updateSongQueue(queueSongs)

        val currentTrackIndex = queueSongs.indexOfFirst { song -> song.id == songId }
        currentQueuePosition = currentTrackIndex

        publishQueue(songQueue)
        saveQueueState(queueSongs)

        val resultTrack = queueSongs.getOrNull(currentTrackIndex) ?: return null
        //return the song to play
        return resultTrack.toPlayableMediaItem()

    }

    override suspend fun getCurrentPlayingSong(): PlayableMediaItem? {
        //Retrieve current playing song
        if (isQueueEmpty())
            return null
        return songQueue.getOrNull(currentQueuePosition)?.toPlayableMediaItem()
    }

    override fun removeSongFromQueue() {
        TODO("Not yet implemented")
    }

    override fun addSongToQueue() {
        TODO("Not yet implemented")
    }

    private fun updatePositionInQueue(isNext: Boolean) {
        //TODO set repeat mode conditionals
        if (isNext) {
            if (currentQueuePosition != songQueue.size - 1)
                currentQueuePosition += 1
            else
            //This is a condition for Repeat All ---
            //TODO add repeat && Shuffle conditions
                currentQueuePosition = 0
        } else {
            //This is previous
            if (currentQueuePosition != 0)
                currentQueuePosition -= 1
            else
                currentQueuePosition = songQueue.size - 1
        }
        publishQueue(songQueue)
    }

    override fun onDestroy(owner: LifecycleOwner) {
        super.onDestroy(owner)
        saveQueueState(songQueue)
        songQueue.getOrNull(currentQueuePosition)?.let {
            preferencesRepository.setCurrentQueuePosition(it.positionInQueue)
        }
        cancel()
    }

    override fun sortSongs() {
        if (isQueueEmpty()) {
            return
        }

        val currentTrack = songQueue.getOrNull(currentQueuePosition) ?: return
        songQueue.sortBy { it.positionInQueue }

        val newTrackIndex =
            songQueue.indexOfFirst { song -> song.positionInQueue == currentTrack.positionInQueue }
        currentQueuePosition = newTrackIndex

        publishQueue(songQueue)
    }

    override fun shuffleSongs() {
        if (isQueueEmpty()) {
            return
        }

        val currentTrack = songQueue.getOrNull(currentQueuePosition) ?: return

        val queue = songQueue.shuffled()
        updateSongQueue(queue)

        val currentTrackIndex =
            queue.indexOfFirst { song -> song.positionInQueue == currentTrack.positionInQueue }

        if (currentTrackIndex != 0) {
            Collections.swap(queue, 0, currentTrackIndex)
        }
        currentQueuePosition = 0

        publishQueue(songQueue)
    }

    private fun saveQueueState(songs: List<MediaItem>) {
        queueStateJob?.cancel()
        queueStateJob = launch {
            songsQueueRepository.updateQueue(songs)
            yield()
        }
    }

    private suspend fun retrieveQueueState(): List<MediaItem> {
        return songsQueueRepository.getQueueSongs()
    }


    private fun Song.toIndexedSong(index: Int): Song {
        return Song(
            this.id,
            this.albumName,
            this.artistId,
            this.artistName,
            this.duration,
            this.title,
            this.trackNumber,
            this.albumId,
            index
        )
    }

    private fun MediaItem.toQueueItem(): MediaSessionCompat.QueueItem {
        val description = MediaDescriptionCompat.Builder()
            .setMediaId(mediaId.toString())
            .setTitle(this.title)
            .setSubtitle(this.artist)
            .setDescription(this.album)
            .build()

        return MediaSessionCompat.QueueItem(description, positionInQueue.toLong())
    }
}