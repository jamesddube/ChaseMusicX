package com.chase.kudzie.chasemusic.data.remote

import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory

object RetrofitServiceFactory {

    private const val BASE_URL = "https://ws.audioscrobbler.com/"

    fun makeLastFMService(isDebug: Boolean): LastFMService {
        val retrofit =
            makeRetrofitInstance(
                makeOkHttpClient(
                    makeLoggingInterceptor(isDebug)
                )
            )
        return makeLastFMService(retrofit)
    }

    private fun makeLastFMService(retrofit: Retrofit): LastFMService {
        return retrofit.create(LastFMService::class.java)
    }

    private fun makeRetrofitInstance(okHttpClient: OkHttpClient) = Retrofit.Builder()
        .baseUrl(BASE_URL)
        .client(okHttpClient)
        .addConverterFactory(MoshiConverterFactory.create().withNullSerialization())
        .build()

    private fun makeOkHttpClient(httpLoggingInterceptor: HttpLoggingInterceptor): OkHttpClient {
        return OkHttpClient.Builder()
            .addInterceptor(httpLoggingInterceptor)
            .build()
    }

    private fun makeLoggingInterceptor(isDebug: Boolean): HttpLoggingInterceptor {
        val logging = HttpLoggingInterceptor()
        logging.level = if (isDebug) {
            HttpLoggingInterceptor.Level.BODY
        } else {
            HttpLoggingInterceptor.Level.NONE
        }
        return logging
    }
}