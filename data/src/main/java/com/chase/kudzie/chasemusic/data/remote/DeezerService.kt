package com.chase.kudzie.chasemusic.data.remote

import com.chase.kudzie.chasemusic.data.model.DeezerArtistEntity
import retrofit2.Response
import retrofit2.http.GET
import retrofit2.http.Query

interface DeezerService {

    @GET("search/artist&limit=1")
    suspend fun getArtistInformation(
        @Query("q") artistName: String
    ): Response<DeezerArtistEntity>

}