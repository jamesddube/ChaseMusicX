package com.chase.kudzie.chasemusic.extensions

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.provider.Settings
import androidx.appcompat.app.AppCompatActivity

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