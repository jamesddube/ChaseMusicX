package com.chase.kudzie.chasemusic.data.repository

import com.chase.kudzie.chasemusic.data.mapper.DeezerMapper
import com.chase.kudzie.chasemusic.data.remote.DeezerService
import com.chase.kudzie.chasemusic.domain.model.DeezerArtist
import com.chase.kudzie.chasemusic.domain.repository.DeezerRepository
import javax.inject.Inject

class DeezerRepositoryImpl @Inject constructor(
    private val service: DeezerService,
    val mapper: DeezerMapper
) : DeezerRepository {
    override suspend fun getArtistInformation(artistName: String): DeezerArtist {
        return if (service.getArtistInformation(artistName).isSuccessful) {
            //Many Assumptions I have made here
            mapper.mapToDomain(service.getArtistInformation(artistName).body()!!)
        } else {
            //Return a dummy Deezer artist
            DeezerArtist(data = arrayListOf(), 0)
        }
    }
}