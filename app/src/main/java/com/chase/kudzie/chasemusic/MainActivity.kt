package com.chase.kudzie.chasemusic

import android.Manifest
import android.os.Bundle
import androidx.fragment.app.Fragment
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.chase.kudzie.chasemusic.base.BaseActivity
import com.chase.kudzie.chasemusic.databinding.ActivityMainBinding
import com.chase.kudzie.chasemusic.extensions.hide
import com.chase.kudzie.chasemusic.extensions.show
import com.chase.kudzie.chasemusic.util.contentView
import dagger.android.AndroidInjection
import dagger.android.AndroidInjector
import dagger.android.DispatchingAndroidInjector
import dagger.android.support.HasSupportFragmentInjector
import javax.inject.Inject

class MainActivity : BaseActivity(), HasSupportFragmentInjector {

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
}
