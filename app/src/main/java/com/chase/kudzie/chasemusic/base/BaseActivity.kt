package com.chase.kudzie.chasemusic.base

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.chase.kudzie.chasemusic.R
import com.chase.kudzie.chasemusic.extensions.goToSettings
import com.chase.kudzie.chasemusic.extensions.isMarshmallow
import com.google.android.material.snackbar.Snackbar

abstract class BaseActivity : AppCompatActivity() {

    private var mHasPermissions: Boolean = false
    private lateinit var permissions: Array<String>
    private var message: String = ""

    companion object {
        const val PERMISSION_REQUEST_CODE = 476
    }

    private fun getPermissionDeniedMessage(): String {
        return getString(R.string.permissions_denied_message)
    }

    open fun getPermissionsArray(): Array<String> {
        return arrayOf()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        permissions = getPermissionsArray()
        mHasPermissions = hasPermissions()
        message = getPermissionDeniedMessage()

    }

    open fun requestPermissions() {
        if (isMarshmallow()) {
            requestPermissions(permissions, PERMISSION_REQUEST_CODE)
        }
    }

    protected fun hasPermissions(): Boolean {
        if (isMarshmallow()) {
            for (permission in permissions) {
                if (checkSelfPermission(permission) != PackageManager.PERMISSION_GRANTED) {
                    return false
                }
            }
        }
        return true
    }

    protected open fun onHasPermissionsChanged(hasPermissions: Boolean) {
        //bla
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (requestCode == PERMISSION_REQUEST_CODE) {
            for (grantResult in grantResults) {
                if (grantResult != PackageManager.PERMISSION_GRANTED) {
                    if (ActivityCompat.shouldShowRequestPermissionRationale(
                            this@BaseActivity,
                            Manifest.permission.WRITE_EXTERNAL_STORAGE
                        )
                    ) {
                        //Show indefinite snack bar for user
                        Snackbar.make(
                            window.decorView,
                            message,
                            Snackbar.LENGTH_INDEFINITE
                        )
                            .setAction(R.string.grant_permission) {
                                requestPermissions()
                            }
                            .setActionTextColor(
                                ContextCompat.getColor(
                                    this@BaseActivity,
                                    R.color.colorAccent
                                )
                            )
                            .show()
                    } else {
                        //Take user to settings
                        Snackbar.make(
                            window.decorView,
                            message,
                            Snackbar.LENGTH_INDEFINITE
                        )
                            .setAction(R.string.action_settings) {
                                val intent = Intent()
                                intent.goToSettings(this@BaseActivity)
                            }
                            .setActionTextColor(
                                ContextCompat.getColor(
                                    this@BaseActivity,
                                    R.color.colorAccent
                                )
                            )
                            .show()
                    }
                    return
                }
            }
            mHasPermissions = true
            onHasPermissionsChanged(true)
        }
    }

    override fun onPostCreate(savedInstanceState: Bundle?) {
        super.onPostCreate(savedInstanceState)
        if (!hasPermissions()) {
            requestPermissions()
        }
    }

    override fun onResume() {
        super.onResume()
        val hasPermissions = hasPermissions()
        if (hasPermissions != mHasPermissions) {
            mHasPermissions = hasPermissions
            if (isMarshmallow()) {
                onHasPermissionsChanged(hasPermissions)
            }
        }
    }


}