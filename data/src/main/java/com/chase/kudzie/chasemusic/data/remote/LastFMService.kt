package com.chase.kudzie.chasemusic.data.remote

import com.chase.kudzie.chasemusic.data.BuildConfig
import com.chase.kudzie.chasemusic.data.model.LastFMArtistEntity
import retrofit2.http.GET
import retrofit2.http.Query

interface LastFMService {

    @GET("2.0/")
    suspend fun getArtistInfo(
        @Query("artist") artistName: String,
        @Query("method") method: String? = "artist.getinfo",
        @Query("api_key") apiKey: String? = BuildConfig.LAST_FM_API_KEY,
        @Query("format") format: String? = "json"
    ): LastFMArtistEntity
}