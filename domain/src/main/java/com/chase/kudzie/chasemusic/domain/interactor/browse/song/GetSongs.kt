package com.chase.kudzie.chasemusic.domain.interactor.browse.song

import com.chase.kudzie.chasemusic.domain.executor.PostExecutionThread
import com.chase.kudzie.chasemusic.domain.interactor.SingleUseCase
import com.chase.kudzie.chasemusic.domain.model.Song
import com.chase.kudzie.chasemusic.domain.repository.AlbumRepository
import com.chase.kudzie.chasemusic.domain.repository.SongRepository
import io.reactivex.Single
import javax.inject.Inject

/**
 * @author Kudzai Chasinda
 * */
class GetSongs @Inject constructor(
    private val repository: SongRepository,
    postExecutionThread: PostExecutionThread
) : SingleUseCase<List<Song>, Nothing>(postExecutionThread) {
    override fun buildUseCaseObservable(params: Nothing?): Single<List<Song>> {
        return repository.getSongs()
    }


}