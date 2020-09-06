package com.chase.kudzie.chasemusic.domain.usecases

import com.chase.kudzie.chasemusic.domain.interactor.browse.song.GetSongs
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import com.chase.kudzie.chasemusic.domain.test.factory.SongFactory
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.assertEquals
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetSongsTest {

    private lateinit var getSongs: GetSongs

    @Mock
    lateinit var songRepository: SongRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getSongs = GetSongs(songRepository)
    }

    @Test
    fun getSongs_fetchesFromRepository() = runBlocking {
        val count = 10
        val songs = SongFactory.generateList(count)

        stubSongRepositoryGetSongs(songs)
        val queriedSongs = songRepository.getSongs()

        assertEquals(songs, queriedSongs)
        assert(queriedSongs.size == count)
    }

    private suspend fun stubSongRepositoryGetSongs(songs: List<Song>) {
        whenever(songRepository.getSongs())
            .thenReturn(songs)
    }


}