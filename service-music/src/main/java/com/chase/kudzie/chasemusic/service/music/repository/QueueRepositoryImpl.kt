package com.chase.kudzie.chasemusic.service.music.repository

import android.content.Context
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.domain.repository.SongQueueRepository
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import com.chase.kudzie.chasemusic.domain.scope.ApplicationContext
import com.chase.kudzie.chasemusic.service.music.extensions.toMediaItem
import com.chase.kudzie.chasemusic.service.music.model.MediaItem
import kotlinx.coroutines.yield
import java.util.*
import javax.inject.Inject
import kotlin.collections.ArrayList

class QueueRepositoryImpl @Inject constructor(
    private val songsQueueRepository: SongQueueRepository,
    private val songRepository: SongRepository
) : QueueRepository {

    private val songQueue = ArrayList<Song>()
    private var currentQueuePosition: Int = -1

    override fun sortSongs() {
        TODO("Not yet implemented")
    }

    override fun shuffleSongs() {
        TODO("Not yet implemented")
    }

    override fun isQueueEmpty(): Boolean {
        return songQueue.isEmpty()
    }

    override suspend fun skipToNext(): MediaItem? {
        if (isQueueEmpty()) {
            return null
        }
        updatePositionInQueue(true)
        //TODO Maybe check if position in queue is valid

        val resultTrack = songQueue[currentQueuePosition]
        return resultTrack.toMediaItem()
    }

    override suspend fun skipToPrevious(): MediaItem? {
        if (isQueueEmpty())
            return null

        updatePositionInQueue(false)
        //TODO Maybe check if position in queue is valid

        val resultTrack = songQueue[currentQueuePosition]
        return resultTrack.toMediaItem()
    }

    override fun prepare() {
        TODO("Not yet implemented")
    }

    override suspend fun onPlayFromMediaId(mediaId: String): MediaItem? {
        //The assumption is that we get all our songs without handling album stuff for now
        //Get all the songs and index them,
        //get the one song with this particular ID
        //update the new songs list with a position in queue
        val allSongs = songRepository.getSongs()
        val queueSongs = allSongs.mapIndexed { index: Int, song: Song ->
            song.toIndexedSong(index)
        }

        songQueue.addAll(queueSongs)

        val currentTrackIndex = queueSongs.indexOfFirst { song -> song.id == mediaId.toLong() }
        currentQueuePosition = currentTrackIndex
        saveQueueState(queueSongs)
        yield()
        val resultTrack = queueSongs.getOrNull(currentTrackIndex) ?: return null
        //return the song to play
        return resultTrack.toMediaItem()

    }

    override suspend fun getCurrentPlayingSong(): MediaItem? {
        //Retrieve current playing song
        if (isQueueEmpty())
            return null
        return songQueue.getOrNull(currentQueuePosition)?.toMediaItem()
    }

    override fun removeSongFromQueue() {
        TODO("Not yet implemented")
    }

    override fun addSongToQueue() {
        TODO("Not yet implemented")
    }

    private fun updatePositionInQueue(isNext: Boolean) {
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
    }

    private suspend fun saveQueueState(songs: List<Song>) {
        songsQueueRepository.updateQueue(songs)
    }

    suspend fun retrieveQueueState(): List<Song> {
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

}