package com.chase.kudzie.chasemusic.domain.interactor.prefs

import com.chase.kudzie.chasemusic.domain.repository.PreferencesRepository
import javax.inject.Inject

class SetRepeatMode @Inject constructor(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke(repeatMode: Int) = repository.setRepeatMode(repeatMode)
}