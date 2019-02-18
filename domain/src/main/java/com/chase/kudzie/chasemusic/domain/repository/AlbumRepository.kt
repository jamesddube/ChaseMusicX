package com.chase.kudzie.chasemusic.domain.repository

import com.chase.kudzie.chasemusic.domain.model.Album

interface AlbumRepository {
    fun getAlbums(): List<Album>
}