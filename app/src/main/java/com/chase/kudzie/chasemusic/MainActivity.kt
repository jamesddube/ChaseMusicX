package com.chase.kudzie.chasemusic

import android.Manifest
import android.os.Bundle
import android.support.v4.media.MediaBrowserCompat
import android.support.v4.media.session.MediaControllerCompat
import android.support.v4.media.session.MediaSessionCompat
import android.view.View
import android.view.ViewTreeObserver
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.fragment.app.Fragment
import androidx.lifecycle.LiveData
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.chase.kudzie.chasemusic.base.BaseActivity
import com.chase.kudzie.chasemusic.databinding.ActivityMainBinding
import com.chase.kudzie.chasemusic.databinding.FragmentPlayerMiniBinding
import com.chase.kudzie.chasemusic.domain.model.MediaIdCategory
import com.chase.kudzie.chasemusic.extensions.hide
import com.chase.kudzie.chasemusic.media.IMediaProvider
import com.chase.kudzie.chasemusic.media.MediaGateway
import com.chase.kudzie.chasemusic.media.connection.OnConnectionChangedListener
import com.chase.kudzie.chasemusic.extensions.playPause
import com.chase.kudzie.chasemusic.extensions.show
import com.chase.kudzie.chasemusic.media.model.MediaMetadata
import com.chase.kudzie.chasemusic.media.model.MediaPlaybackState
import com.chase.kudzie.chasemusic.ui.nowplaying.PlayerMiniFragment
import com.chase.kudzie.chasemusic.util.contentView
import com.google.android.material.bottomsheet.BottomSheetBehavior
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.MainScope
import kotlinx.coroutines.flow.Flow
import timber.log.Timber
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

    private lateinit var behavior: BottomSheetBehavior<ConstraintLayout>

    private lateinit var fragmentMiniPlayer: PlayerMiniFragment

    private val bottomSheetCallback = object : BottomSheetBehavior.BottomSheetCallback() {
        override fun onStateChanged(bottomSheet: View, newState: Int) {
            when (newState) {
                BottomSheetBehavior.STATE_EXPANDED -> {
                    Timber.e("SHEET EXPANDED")
                }
                BottomSheetBehavior.STATE_COLLAPSED -> {
                    Timber.e("SHEET COLLAPSED")
                }
            }
        }

        override fun onSlide(bottomSheet: View, slideOffset: Float) {
            hideBottomNavOnDrag(slideOffset)
        }

    }

    private fun hideBottomNavOnDrag(slideOffset: Float) {
        val alpha = 1 - slideOffset

        //TODO Maybe create a listener and do this inside the fragment instead?
        fragmentMiniPlayer.view?.alpha = alpha
        fragmentMiniPlayer.view?.visibility = if (alpha == 0f) View.GONE else View.VISIBLE

        binding.bottomNav.translationY = slideOffset * 500
        binding.bottomNav.alpha = alpha
    }

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
                    R.id.artists -> {
                        bottomNav.show()
                        recalculateLayout(true)
                    }
                    else -> {
                        bottomNav.hide()
                        recalculateLayout(false)
                    }
                }
            }

            //Init Bottom bar
            behavior = BottomSheetBehavior.from(layoutBottomSheet.playerBottomSheet)
            behavior.addBottomSheetCallback(bottomSheetCallback)

            //TODO unsafe call, maybe null
            fragmentMiniPlayer =
                supportFragmentManager.findFragmentById(R.id.player_mini_fragment) as PlayerMiniFragment

            fragmentMiniPlayer.view?.setOnClickListener {
                behavior.state = BottomSheetBehavior.STATE_EXPANDED
            }
        }
    }

    private fun recalculateLayout(isShowing: Boolean) {
        binding.layoutBottomSheet.playerBottomSheet.viewTreeObserver.addOnGlobalLayoutListener(
            object :
                ViewTreeObserver.OnGlobalLayoutListener {
                override fun onGlobalLayout() {

                    behavior.isHideable = false
                    if (isShowing) {
                        behavior.peekHeight = binding.bottomNav.height * 2
                    } else {
                        behavior.peekHeight = binding.bottomNav.height
                    }

                    binding.layoutBottomSheet.playerBottomSheet.viewTreeObserver.removeOnGlobalLayoutListener(
                        this
                    )
                }
            })
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

    override fun onDestroy() {
        super.onDestroy()

    }

    fun connect() {
        mediaGateway.connect()
    }

    override fun playMediaFromId(mediaIdCategory: MediaIdCategory) {
        transportControls()?.playFromMediaId(mediaIdCategory.toString(), null)
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

    override fun prepare() {
        transportControls()?.prepare()
    }

    override fun stop() {
        transportControls()?.stop()
    }

    override fun seekTo(pos: Long) {
        transportControls()?.seekTo(pos)
    }

    override fun skipToNext() {
        transportControls()?.skipToNext()
    }

    override fun skipToPrevious() {
        transportControls()?.skipToPrevious()
    }

    override fun skipToQueueItem(id: Long) {
        //TODO implement core functionality
        transportControls()?.skipToQueueItem(id)
    }

    override fun favouriteSong(songId: Long) {
        TODO("Needs a custom action to be set in Transport controls and handled")

    }

    override fun observeMetadata(): LiveData<MediaMetadata> {
        return mediaGateway.observeMetadata()
    }

    override fun observePlaybackState(): LiveData<MediaPlaybackState> {
        return mediaGateway.observePlaybackState()
    }


}
