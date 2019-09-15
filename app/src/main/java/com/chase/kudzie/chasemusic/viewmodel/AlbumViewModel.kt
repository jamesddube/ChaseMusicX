package com.chase.kudzie.chasemusic.viewmodel

import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chase.kudzie.chasemusic.domain.interactor.browse.album.GetAlbums
import com.chase.kudzie.chasemusic.domain.model.Album
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class AlbumViewModel @Inject constructor(
    private val getAlbums: GetAlbums
) : ViewModel() {

    private val _albums = MutableLiveData<List<Album>>()
    val albums: LiveData<List<Album>> = _albums

    init {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _albums.value = getAlbums()
            }
        }
    }

}