package com.chase.kudzie.chasemusic.domain.interactor.queue

import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.domain.repository.SongQueueRepository
import javax.inject.Inject

class UpdateQueue @Inject constructor(
    private val repository: SongQueueRepository
) {
    suspend operator fun invoke(list: List<Song>) = repository.updateQueue(list)
}