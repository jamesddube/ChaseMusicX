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
class FindAlbums @Inject constructor(
    private val repository: AlbumRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Album>, FindAlbums.Params>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Single<List<Album>> {
        if (params == null) throw IllegalArgumentException("Param searchString cannot be null")
        return repository.findAlbums(params.searchString)
    }

    data class Params(val searchString: String) {
        companion object {
            fun forAlbum(searchString: String): Params = Params(searchString)
        }
    }
}