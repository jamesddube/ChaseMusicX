package com.chase.kudzie.chasemusic

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.Toast
import androidx.lifecycle.Observer
import androidx.lifecycle.ViewModelProviders
import com.chase.kudzie.chasemusic.injection.ViewModelFactory
import com.chase.kudzie.chasemusic.viewmodel.AlbumViewModel
import dagger.android.AndroidInjection
import dagger.android.HasActivityInjector
import timber.log.Timber
import javax.inject.Inject

class MainActivity : AppCompatActivity() {

    @Inject
    lateinit var viewModelFactory: ViewModelFactory

    lateinit var viewModel: AlbumViewModel

    override fun onCreate(savedInstanceState: Bundle?) {
        AndroidInjection.inject(this)
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

        initViewModels()

        //Quick test
        viewModel.albums.observe(this, Observer {
            it?.let {
                Timber.e("Size of Albums:: ${it.size}")
                Toast.makeText(this, "Size of Albums:: ${it.size}", Toast.LENGTH_LONG).show()
            }
        })
    }

    private fun initViewModels() {
        viewModel = ViewModelProviders.of(this, viewModelFactory).get(AlbumViewModel::class.java)
    }
}
