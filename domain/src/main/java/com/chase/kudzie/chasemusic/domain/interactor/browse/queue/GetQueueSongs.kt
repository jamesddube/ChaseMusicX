package com.chase.kudzie.chasemusic.domain.interactor.browse.queue

import com.chase.kudzie.chasemusic.domain.repository.QueueRepository
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class GetQueueSongs @Inject constructor(
    val repository: QueueRepository
) {
    suspend operator fun invoke() = repository.getQueueSongs()
}