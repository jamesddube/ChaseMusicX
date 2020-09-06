package com.chase.kudzie.chasemusic.domain.usecases

import com.chase.kudzie.chasemusic.domain.interactor.browse.artists.GetArtists
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.domain.repository.ArtistRepository
import com.chase.kudzie.chasemusic.domain.test.factory.ArtistFactory
import com.nhaarman.mockitokotlin2.whenever
import kotlinx.coroutines.runBlocking
import org.junit.Assert.*
import org.junit.Before
import org.junit.Test
import org.junit.runner.RunWith
import org.mockito.Mock
import org.mockito.MockitoAnnotations
import org.mockito.junit.MockitoJUnitRunner

@RunWith(MockitoJUnitRunner::class)
class GetArtistsTest {

    private lateinit var getArtists: GetArtists

    @Mock
    lateinit var artistRepository: ArtistRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getArtists = GetArtists(repository = artistRepository)
    }

    @Test
    fun getArtists_fetchesFromRepository() = runBlocking {
        val count = 3
        val artists = ArtistFactory.generateList(count)
        stubArtistRepositoriesGetArtists(artists)

        val queriedArtists = artistRepository.getArtists()

        assertEquals(artists, queriedArtists)
        assertTrue(queriedArtists.size == count)
    }

    private suspend fun stubArtistRepositoriesGetArtists(artists: List<Artist>) {
        whenever(artistRepository.getArtists())
            .thenReturn(artists)
    }
}
