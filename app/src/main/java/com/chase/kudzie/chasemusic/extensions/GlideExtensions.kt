package com.chase.kudzie.chasemusic.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import com.chase.kudzie.chasemusic.R
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine


suspend fun Context.retrieveGlideBitmap(url: String): Bitmap? = suspendCoroutine { continuation ->
    Glide.with(this)
        .asBitmap()
        .load(url)
        .placeholder(R.drawable.ic_albums)
        .circleCrop()
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                continuation.safeResume(null)
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                continuation.safeResume(resource)
            }
        })
}

suspend fun Context.retrieveGlideBitmap(uri: Uri): Bitmap? = suspendCoroutine { continuation ->
    Glide.with(this)
        .asBitmap()
        .load(uri)
        .placeholder(R.drawable.ic_albums)
        .into(object : CustomTarget<Bitmap>() {
            override fun onLoadCleared(placeholder: Drawable?) {
                continuation.safeResume(null)
            }

            override fun onResourceReady(resource: Bitmap, transition: Transition<in Bitmap>?) {
                continuation.safeResume(resource)
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