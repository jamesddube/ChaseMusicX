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
class FindArtists @Inject constructor(
    private val repository: ArtistRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Artist>, FindArtists.Params>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Single<List<Artist>> {
        if (params == null) throw IllegalArgumentException("Param searchString cannot be null")
        return repository.findArtists(params.searchString)
    }


    data class Params(val searchString: String) {
        companion object {
            fun forArtist(searchString: String): Params = Params(searchString)
        }
    }
}