package com.chase.kudzie.chasemusic.domain.repository

import com.chase.kudzie.chasemusic.domain.model.LastFMArtist

interface LastFMRepository {
    suspend fun getArtistInfo(artistName:String): LastFMArtist
}