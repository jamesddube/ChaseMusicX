package com.chase.kudzie.chasemusic.service.music.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.kudziechase.chasemusic.service.music.R
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Context.retrieveGlideBitmap(uri: Uri): Bitmap? = suspendCoroutine { continuation ->
    Glide.with(this)
        .asBitmap()
        .load(uri)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                continuation.safeResume(null)
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                continuation.safeResume(resource)
            }

            override fun onLoadFailed(errorDrawable: Drawable?) {
                Glide.with(this@retrieveGlideBitmap)
                    .asBitmap()
                    .load(R.drawable.placeholder)
                    .into(object : CustomTarget<Bitmap>() {
                        override fun onResourceReady(
                            resource: Bitmap,
                            transition: Transition<in Bitmap>?
                        ) {
                            continuation.safeResume(resource)
                        }

                        override fun onLoadCleared(placeholder: Drawable?) {
                            try {
                                continuation.safeResume(null)
                            } catch (ex: Exception) {

                            }
                        }
                        override fun onLoadFailed(errorDrawable: Drawable?) {
                            continuation.safeResume(null)
                        }
                    })
            }
        })
}

fun <T> Continuation<T?>.safeResume(item: T?) {
    try {
        resume(item)
    } catch (ex: Throwable) {
        ex.printStackTrace()
        // already resumed
    }
}