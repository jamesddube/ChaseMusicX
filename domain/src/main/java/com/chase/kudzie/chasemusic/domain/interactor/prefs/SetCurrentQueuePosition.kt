package com.chase.kudzie.chasemusic.domain.interactor.prefs

import com.chase.kudzie.chasemusic.domain.repository.PreferencesRepository
import javax.inject.Inject

class SetCurrentQueuePosition @Inject constructor(
    private val repository: PreferencesRepository
) {
    suspend operator fun invoke(positionInQueue: Int) =
        repository.setCurrentQueuePosition(positionInQueue)
}