package com.chase.kudzie.chasemusic.extensions

import android.Manifest
import android.content.Context
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.core.content.ContextCompat

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