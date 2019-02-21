package com.chase.kudzie.chasemusic.domain.interactor.albums

import com.chase.kudzie.chasemusic.domain.executor.PostExecutionThread
import com.chase.kudzie.chasemusic.domain.interactor.SingleUseCase
import com.chase.kudzie.chasemusic.domain.model.Album
import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import io.reactivex.Single
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class GetAlbum @Inject constructor(
    private val repository: AlbumRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Album, GetAlbum.Params>(postExecutionThread) {

    override fun buildUseCaseObservable(params: Params?): Single<Album> {
        if (params == null) throw IllegalArgumentException("id for album cannot be null")
        return repository.getAlbum(params.id)
    }

    data class Params(val id: Long) {
        companion object {
            fun forAlbum(id: Long): Params = Params(id)
        }
    }
}