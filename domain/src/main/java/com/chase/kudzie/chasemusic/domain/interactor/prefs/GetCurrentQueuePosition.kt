package com.chase.kudzie.chasemusic.domain.interactor.prefs

import com.chase.kudzie.chasemusic.domain.repository.PreferencesRepository
import javax.inject.Inject

class GetCurrentQueuePosition @Inject constructor(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke() = repository.getCurrentQueuePosition()
}