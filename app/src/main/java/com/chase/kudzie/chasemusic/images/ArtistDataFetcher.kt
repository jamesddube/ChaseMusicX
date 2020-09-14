package com.chase.kudzie.chasemusic.images

import com.bumptech.glide.Priority
import com.bumptech.glide.load.DataSource
import com.bumptech.glide.load.data.DataFetcher
import com.bumptech.glide.load.data.HttpUrlFetcher
import com.bumptech.glide.load.model.GlideUrl
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.domain.model.DeezerArtist
import com.chase.kudzie.chasemusic.domain.repository.DeezerRepository
import com.chase.kudzie.chasemusic.shared.injection.coroutinescope.DefaultScope
import kotlinx.coroutines.*
import java.io.InputStream
import java.lang.RuntimeException

class ArtistDataFetcher(
    val artist: Artist,
    private val deezerRepository: DeezerRepository
) : DataFetcher<InputStream>, CoroutineScope by DefaultScope() {

    companion object {
        const val REQUEST_TIMEOUT = 4000
    }

    override fun loadData(priority: Priority, callback: DataFetcher.DataCallback<in InputStream>) {
        launch {
            try {
                //Make request to backend and perform checks
                delay(3000)
                yield()

                val cleanArtist =
                    when {
                        artist.name.contains(",") -> artist.name.split(",")[0]
                        artist.name.contains(";") -> artist.name.split(";")[0]
                        else -> artist.name
                    }

                val deezerArtist =
                    async { deezerRepository.getArtistInformation(artistName = cleanArtist) }.await()
                yield()
                val image = retrieveBestQuality(deezerArtist)
                yield()

                if (image.isNotBlank()) {
                    val urlFetcher = HttpUrlFetcher(
                        GlideUrl(image),
                        REQUEST_TIMEOUT
                    )
                    urlFetcher.loadData(priority, callback)
                }

                return@launch
            } catch (ex: Throwable) {
                callback.onLoadFailed(RuntimeException(ex))
            }

        }
    }

    private fun retrieveBestQuality(deezerArtist: DeezerArtist): String {
        //Attempts to retrieve best quality
        return if (deezerArtist.total > 0) {
            val data = deezerArtist.data[0]
            when {
                data.pictureXL.isNotEmpty() -> data.pictureXL
                data.pictureBig.isNotEmpty() -> data.pictureBig
                data.pictureMedium.isNotEmpty() -> data.pictureMedium
                data.pictureSmall.isNotEmpty() -> data.pictureSmall
                data.picture.isNotEmpty() -> data.picture
                else -> ""
            }
        } else {
            ""
        }
    }

    override fun cleanup() {

    }

    override fun cancel() {
        cancel(null)
    }

    override fun getDataClass(): Class<InputStream> = InputStream::class.java

    override fun getDataSource(): DataSource = DataSource.REMOTE
}