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
class GetSong @Inject constructor(
    private val repository: SongRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<Song, GetSong.Params>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Params?): Single<Song> {
        if (params == null) throw IllegalArgumentException("Param id cannot be null")
        return repository.getSong(params.id)
    }

    data class Params(val id: Long) {
        companion object {
            fun forSong(id: Long): Params = Params(id)
        }
    }
}