package com.chase.kudzie.chasemusic.service.music.repository

import com.chase.kudzie.chasemusic.domain.interactor.songs.GetSongsByCategory
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.domain.repository.SongQueueRepository
import com.chase.kudzie.chasemusic.service.music.extensions.toMediaItem
import com.chase.kudzie.chasemusic.service.music.extensions.toPlayableMediaItem
import com.chase.kudzie.chasemusic.domain.model.MediaItem
import com.chase.kudzie.chasemusic.service.music.model.PlayableMediaItem
import kotlinx.coroutines.yield
import javax.inject.Inject
import kotlin.collections.ArrayList

class QueueRepositoryImpl @Inject constructor(
    private val songsQueueRepository: SongQueueRepository,
    private val getSongsByCategory: GetSongsByCategory,
) : QueueRepository {

    private val songQueue = ArrayList<MediaItem>()
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

    override suspend fun skipToNext(): PlayableMediaItem? {
        if (isQueueEmpty()) {
            return null
        }
        updatePositionInQueue(true)
        //TODO Maybe check if position in queue is valid

        val resultTrack = songQueue[currentQueuePosition]
        return resultTrack.toPlayableMediaItem()
    }

    override suspend fun skipToPrevious(): PlayableMediaItem? {
        if (isQueueEmpty())
            return null

        updatePositionInQueue(false)
        //TODO Maybe check if position in queue is valid

        val resultTrack = songQueue[currentQueuePosition]
        return resultTrack.toPlayableMediaItem()
    }

    override fun prepare() {
        TODO("Not yet implemented")
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

        songQueue.clear()
        songQueue.addAll(queueSongs)

        val currentTrackIndex = queueSongs.indexOfFirst { song -> song.id == songId }
        currentQueuePosition = currentTrackIndex
        saveQueueState(queueSongs)
        yield()
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

    private suspend fun saveQueueState(songs: List<MediaItem>) {
        songsQueueRepository.updateQueue(songs)
    }

    suspend fun retrieveQueueState(): List<MediaItem> {
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