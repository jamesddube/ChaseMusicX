package com.chase.kudzie.chasemusic.domain.repository

import com.chase.kudzie.chasemusic.domain.model.DeezerArtist

interface DeezerRepository {
    suspend fun getArtistInformation(artistName:String): DeezerArtist
}