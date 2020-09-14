package com.chase.kudzie.chasemusic.ui.artists

import android.util.Log
import androidx.lifecycle.LiveData
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.ViewModel
import androidx.lifecycle.viewModelScope
import com.chase.kudzie.chasemusic.domain.interactor.artists.GetArtist
import com.chase.kudzie.chasemusic.domain.interactor.browse.artists.GetArtists
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.domain.model.DeezerArtist
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.launch
import kotlinx.coroutines.withContext
import javax.inject.Inject

class ArtistsViewModel @Inject constructor(
    private val getArtists: GetArtists,
    private val _getArtist: GetArtist,
) : ViewModel() {

    private val _artists = MutableLiveData<List<Artist>>()
    val artists: LiveData<List<Artist>> = _artists

    private val _artist = MutableLiveData<Artist>()
    val artist: LiveData<Artist> = _artist

    init {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _artists.value = getArtists()
                Log.e("DO", "NOthing")
            }
        }
    }

    fun getArtist(artistId: Long) {
        viewModelScope.launch(Dispatchers.IO) {
            withContext(Dispatchers.Main) {
                _artist.value = _getArtist(GetArtist.Params.forArtist(artistId))
            }
        }
    }


}