package com.chase.kudzie.chasemusic.domain.interactor.artists

import com.chase.kudzie.chasemusic.domain.executor.PostExecutionThread
import com.chase.kudzie.chasemusic.domain.interactor.SingleUseCase
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.domain.repository.ArtistRepository
import io.reactivex.Single
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class GetArtist @Inject constructor(
    private val repository: ArtistRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Artist, GetArtist.Params>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Single<Artist> {
        if (params == null) throw IllegalArgumentException("Param id cannot be null")
        return repository.getArtist(params.id)
    }

    data class Params(val id: Long) {
        companion object {
            fun forArtist(id: Long): Params = Params(id)
        }
    }
}