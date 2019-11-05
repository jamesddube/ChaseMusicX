package com.chase.kudzie.chasemusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import androidx.navigation.Navigation
import androidx.navigation.ui.setupWithNavController
import com.chase.kudzie.chasemusic.databinding.ActivityMainBinding
import com.chase.kudzie.chasemusic.extensions.hide
import com.chase.kudzie.chasemusic.extensions.show
import com.chase.kudzie.chasemusic.util.contentView
import dagger.android.AndroidInjection

class MainActivity : AppCompatActivity() {

    private val binding: ActivityMainBinding by contentView(R.layout.activity_main)

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)

        binding.apply {
            val navigationController =
                Navigation.findNavController(this@MainActivity, R.id.nav_host)
            bottomNav.setupWithNavController(navigationController)

            navigationController.addOnDestinationChangedListener { _, destination, _ ->
                when (destination.id) {
                    R.id.songs, R.id.playlists, R.id.albums, R.id.artists -> bottomNav.show()
                    else -> bottomNav.hide()
                }
            }
        }
    }
}
