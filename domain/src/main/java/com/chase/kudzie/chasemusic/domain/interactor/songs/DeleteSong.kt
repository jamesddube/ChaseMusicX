package com.chase.kudzie.chasemusic.domain.interactor.songs

import com.chase.kudzie.chasemusic.domain.executor.PostExecutionThread
import com.chase.kudzie.chasemusic.domain.interactor.CompletableUseCase
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import io.reactivex.Completable
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class DeleteSong @Inject constructor(
    private val repository: SongRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<DeleteSong.Params>(postExecutionThread) {
    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Param id cannot be null")
        return repository.deleteSong(params.id)
    }

    data class Params(val id: Long) {
        companion object {
            fun forSong(id: Long): Params = Params(id)
        }
    }
}