package com.chase.kudzie.chasemusic.domain.interactor.albums

import com.chase.kudzie.chasemusic.domain.executor.PostExecutionThread
import com.chase.kudzie.chasemusic.domain.interactor.CompletableUseCase
import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import io.reactivex.Completable
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class DeleteAlbum @Inject constructor(
    private val repository: AlbumRepository,
    postExecutionThread: PostExecutionThread
) : CompletableUseCase<DeleteAlbum.Params>(postExecutionThread) {
    override fun buildUseCaseCompletable(params: Params?): Completable {
        if (params == null) throw IllegalArgumentException("Param id cannot be null")
        return repository.deleteAlbum(params.id)
    }

    data class Params(val id: Long) {
        companion object {
            fun forAlbum(id: Long): Params = Params(id)
        }
    }
}