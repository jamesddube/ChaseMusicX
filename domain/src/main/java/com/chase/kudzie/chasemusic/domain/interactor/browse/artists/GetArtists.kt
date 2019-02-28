package com.chase.kudzie.chasemusic.domain.interactor.browse.artists

import com.chase.kudzie.chasemusic.domain.executor.PostExecutionThread
import com.chase.kudzie.chasemusic.domain.interactor.SingleUseCase
import com.chase.kudzie.chasemusic.domain.model.Artist
import com.chase.kudzie.chasemusic.domain.repository.ArtistRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class GetArtists @Inject constructor(
    private val repository: ArtistRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Artist>, Nothing>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Nothing?): Single<List<Artist>> {
        return repository.getArtists()
    }
}