package com.chase.kudzie.chasemusic.data.repository

import com.chase.kudzie.chasemusic.data.mapper.LastFMMapper
import com.chase.kudzie.chasemusic.data.remote.LastFMService
import com.chase.kudzie.chasemusic.domain.model.LastFMArtist
import com.chase.kudzie.chasemusic.domain.repository.LastFMRepository
import javax.inject.Inject

class LastFMRepositoryImpl @Inject constructor(
    private val service: LastFMService,
    val mapper: LastFMMapper
) : LastFMRepository {

    override suspend fun getArtistInfo(artistName: String): LastFMArtist {
        return mapper.mapToDomain(service.getArtistInfo(artistName))
    }
}