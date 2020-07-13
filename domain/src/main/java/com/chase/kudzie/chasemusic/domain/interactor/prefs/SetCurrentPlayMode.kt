package com.chase.kudzie.chasemusic.domain.interactor.prefs

import com.chase.kudzie.chasemusic.domain.repository.PreferencesRepository
import javax.inject.Inject

class SetCurrentPlayMode @Inject constructor(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke(playMode: Int) = repository.setCurrentPlayMode(playMode)
}