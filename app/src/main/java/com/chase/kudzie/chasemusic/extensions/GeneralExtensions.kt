package com.chase.kudzie.chasemusic.extensions

import android.Manifest
import android.annotation.SuppressLint
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.Color
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.annotation.AttrRes
import androidx.annotation.ColorInt
import androidx.core.content.ContextCompat
import androidx.core.content.res.use
import androidx.lifecycle.LiveData
import androidx.lifecycle.Transformations

/**
 * Method checks if device OS is Android 6.0 or greater.
 */
fun isMarshmallow(): Boolean {
    return Build.VERSION.SDK_INT >= Build.VERSION_CODES.M
}

/**
 * Allows user to navigate to Settings for current Application.
 */
fun Intent.goToSettings(activity: Context) {
    this.action = Settings.ACTION_APPLICATION_DETAILS_SETTINGS
    val uri: Uri = Uri.fromParts(
        "package",
        activity.packageName,
        null
    )
    this.data = uri
    activity.startActivity(this)
}

/**
 * Checks whether or not we have read storage permission
 * */
fun canReadStorage(context: Context): Boolean {
    return ContextCompat.checkSelfPermission(
        context,
        Manifest.permission.WRITE_EXTERNAL_STORAGE
    ) == PackageManager.PERMISSION_GRANTED
}

@ColorInt
@SuppressLint("Recycle")
fun Context.themeColor(
    @AttrRes themeAttrId: Int
): Int {
    return obtainStyledAttributes(
        intArrayOf(themeAttrId)
    ).use {
        it.getColor(0, Color.MAGENTA)
    }
}

inline fun <T> LiveData<T>.distinctUntilChanged(): LiveData<T> {
    return Transformations.distinctUntilChanged(this)
}