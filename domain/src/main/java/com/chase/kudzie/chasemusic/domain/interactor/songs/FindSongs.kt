package com.chase.kudzie.chasemusic.domain.interactor.songs

import com.chase.kudzie.chasemusic.domain.executor.PostExecutionThread
import com.chase.kudzie.chasemusic.domain.interactor.SingleUseCase
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import io.reactivex.Single
import java.lang.IllegalArgumentException
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 */
class FindSongs @Inject constructor(
    private val repository: SongRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Song>, FindSongs.Params>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Single<List<Song>> {
        if (params == null) throw IllegalArgumentException("Param searchString cannot be null")
        return repository.findSongs(params.searchString)
    }


    data class Params(val searchString: String) {
        companion object {
            fun forSong(searchString: String): Params = Params(searchString)
        }
    }
}