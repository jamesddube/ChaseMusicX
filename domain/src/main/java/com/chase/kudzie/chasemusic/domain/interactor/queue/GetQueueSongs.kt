package com.chase.kudzie.chasemusic.domain.interactor.queue

import com.chase.kudzie.chasemusic.domain.repository.SongQueueRepository
import javax.inject.Inject

class GetQueueSongs @Inject constructor(
    private val repository: SongQueueRepository
) {
    suspend operator fun invoke() = repository.getQueueSongs()
}