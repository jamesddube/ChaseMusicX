package com.chase.kudzie.chasemusic.domain.usecases

import com.chase.kudzie.chasemusic.domain.interactor.browse.album.GetAlbums
import com.chase.kudzie.chasemusic.domain.model.Album
import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import com.chase.kudzie.chasemusic.domain.test.factory.AlbumFactory
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
class GetAlbumsTest {

    private lateinit var getAlbums: GetAlbums

    @Mock
    lateinit var albumRepository: AlbumRepository

    @Before
    fun setup() {
        MockitoAnnotations.initMocks(this)
        getAlbums = GetAlbums(albumRepository)
    }

    @Test
    fun getAlbums_fetchesFromRepository() = runBlocking {
        val count = 2
        val albums = AlbumFactory.generateList(count)
        stubAlbumsRepositoryGetAlbums(albums)

        val queriedAlbums = albumRepository.getAlbums()

        assertEquals(albums, queriedAlbums)
        assert(queriedAlbums.size == count)
    }

    private suspend fun stubAlbumsRepositoryGetAlbums(albums: List<Album>) {
        whenever(albumRepository.getAlbums())
            .thenReturn(albums)
    }
}