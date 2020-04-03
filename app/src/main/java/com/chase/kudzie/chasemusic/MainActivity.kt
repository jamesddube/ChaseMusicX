package com.chase.kudzie.chasemusic

import android.Manifest
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.chase.kudzie.chasemusic.base.BaseActivity
import com.chase.kudzie.chasemusic.databinding.ActivityMainBinding
import com.chase.kudzie.chasemusic.extensions.hide
import com.chase.kudzie.chasemusic.media.IMediaProvider
import com.chase.kudzie.chasemusic.media.MediaGateway
import com.chase.kudzie.chasemusic.media.connection.OnConnectionChangedListener
import com.chase.kudzie.chasemusic.extensions.playPause
import com.chase.kudzie.chasemusic.extensions.show
import com.chase.kudzie.chasemusic.util.contentView
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import javax.inject.Inject

class MainActivity :
    BaseActivity(),
    HasSupportFragmentInjector,
    IMediaProvider,
    OnConnectionChangedListener,
    CoroutineScope by MainScope() {

    private val mediaGateway by lazy(LazyThreadSafetyMode.NONE) {
        MediaGateway(
            this,
            this
        )
    }

    @Inject
    lateinit var fragmentInjector: DispatchingAndroidInjector<Fragment>

    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        if (hasPermissions()) {
            applyBinding()
        }
    }

    private fun applyBinding() {
        binding.apply {
            val navigationController =
                Navigation.findNavController(this@MainActivity, R.id.nav_host)
            bottomNav.setupWithNavController(navigationController)

            navigationController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.songs,
                    R.id.playlists,
                    R.id.albums,
                    R.id.artists -> bottomNav.show()
                    else -> bottomNav.hide()
                }
            }
        }
    }

    override fun onHasPermissionsChanged(hasPermissions: Boolean) {
        super.onHasPermissionsChanged(hasPermissions)
        if (hasPermissions) {
            applyBinding()
        }
    }

    override fun getPermissionsArray(): Array<String> {
        return arrayOf(
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.WRITE_EXTERNAL_STORAGE
        )
    }

    override fun supportFragmentInjector(): AndroidInjector<Fragment> {
        return fragmentInjector
    }

    override fun onStart() {
        super.onStart()
        connect()
    }

    override fun onStop() {
        super.onStop()
        mediaGateway.disconnect()
        unregisterMediaController()
    }

    fun connect() {
        mediaGateway.connect()
    }

    override fun playMediaFromId(mediaId: String) {
        transportControls()?.playFromMediaId(mediaId, null)
    }

    override fun playPause() {
        mediaController()?.playPause()
    }

    override fun onConnectionSuccess(
        mediaBrowser: MediaBrowserCompat,
        callback: MediaControllerCompat.Callback
    ) {
        try {
            registerMediaController(mediaBrowser.sessionToken, callback)
            mediaGateway.initialize(MediaControllerCompat.getMediaController(this))
        } catch (ex: Throwable) {
            ex.printStackTrace()
            onConnectionFailed(mediaBrowser, callback)
        }
    }

    override fun onConnectionFailed(
        mediaBrowser: MediaBrowserCompat,
        callback: MediaControllerCompat.Callback
    ) {
        unregisterMediaController()
    }

    private fun unregisterMediaController() {
        val mediaController = MediaControllerCompat.getMediaController(this)
        if (mediaController != null) {
            mediaController.unregisterCallback(mediaGateway.callback)
            MediaControllerCompat.setMediaController(this, null)
        }
    }

    private fun registerMediaController(
        token: MediaSessionCompat.Token,
        callback: MediaControllerCompat.Callback
    ) {
        val mediaController = MediaControllerCompat(this, token)
        mediaController.registerCallback(callback)
        MediaControllerCompat.setMediaController(this, mediaController)
    }

    private fun mediaController(): MediaControllerCompat? {
        return MediaControllerCompat.getMediaController(this)
    }

    private fun transportControls(): MediaControllerCompat.TransportControls? {
        return mediaController()?.transportControls
    }


}
