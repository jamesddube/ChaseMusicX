package com.chase.kudzie.chasemusic.service.music.extensions

import android.content.Context
import android.graphics.Bitmap
import android.graphics.drawable.Drawable
import android.net.Uri
import com.bumptech.glide.Glide
import com.bumptech.glide.request.target.CustomTarget
import com.bumptech.glide.request.transition.Transition
import kotlin.coroutines.Continuation
import kotlin.coroutines.resume
import kotlin.coroutines.suspendCoroutine

suspend fun Context.retrieveGlideBitmap(uri: Uri): Bitmap? = suspendCoroutine { continuation ->
    //TODO maybe add a placeholder image incase Glide cannot find anything
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