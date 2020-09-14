package com.chase.kudzie.chasemusic.data.remote

import android.content.Context
import com.squareup.moshi.Moshi
import com.squareup.moshi.kotlin.reflect.KotlinJsonAdapterFactory
import okhttp3.Cache
import okhttp3.Interceptor
import okhttp3.OkHttpClient
import okhttp3.logging.HttpLoggingInterceptor
import retrofit2.Retrofit
import retrofit2.converter.moshi.MoshiConverterFactory
import java.io.File
import java.util.*

object RetrofitServiceFactory {

    private const val LAST_FM_BASE_URL = "https://ws.audioscrobbler.com/"
    private const val DEEZER_BASE_URL = "https://api.deezer.com/"

    private val moshi: Moshi = Moshi.Builder()
        .add(KotlinJsonAdapterFactory())
        .build()

    fun makeLastFMService(isDebug: Boolean): LastFMService {
        val retrofit =
            makeRetrofitInstance(
                makeOkHttpClient(
                    makeLoggingInterceptor(isDebug)
                ), baseUrl = LAST_FM_BASE_URL
            )
        return makeLastFMService(retrofit)
    }

    fun makeDeezerService(context: Context, isDebug: Boolean): DeezerService {
        val retrofit =
            makeRetrofitInstance(
                makeCacheOkHttpClient(context, makeLoggingInterceptor(isDebug)),
                baseUrl = DEEZER_BASE_URL
            )
        return makeDeezerService(retrofit)
    }

    private fun makeDeezerService(retrofit: Retrofit): DeezerService {
        return retrofit.create(DeezerService::class.java)
    }

    private fun makeLastFMService(retrofit: Retrofit): LastFMService {
        return retrofit.create(LastFMService::class.java)
    }

    private fun makeRetrofitInstance(okHttpClient: OkHttpClient, baseUrl: String) =
        Retrofit.Builder()
            .baseUrl(baseUrl)
            .client(okHttpClient)
            .addConverterFactory(MoshiConverterFactory.create(moshi))
            .build()


    private fun makeCacheOkHttpClient(
        context: Context,
        httpLoggingInterceptor: HttpLoggingInterceptor
    ): OkHttpClient {
        return OkHttpClient.Builder()
            .cache(createDefaultCache(context))
            .addInterceptor(httpLoggingInterceptor)
            .addInterceptor(makeCacheControlInterceptor())
            .build()
    }

    private fun createDefaultCache(
        context: Context
    ): Cache? {
        val cacheDir = File(context.applicationContext.cacheDir.absolutePath, "/cm-deezer-cache/")
        if (cacheDir.mkdir() or cacheDir.isDirectory) {
            return Cache(cacheDir, 1024 * 1024 * 10)
        }
        return null
    }

    private fun makeCacheControlInterceptor(): Interceptor {
        return Interceptor { chain ->
            val modifiedRequest = chain.request().newBuilder()
                .addHeader(
                    "Cache-Control",
                    String.format(
                        Locale.getDefault(),
                        "max-age=31536000, max-stale=31536000"
                    )
                ).build()
            chain.proceed(modifiedRequest)
        }
    }

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