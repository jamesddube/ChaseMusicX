package com.chase.kudzie.chasemusic.domain.interactor

import com.chase.kudzie.chasemusic.domain.executor.PostExecutionThread
import io.reactivex.Completable
import io.reactivex.CompletableObserver
import io.reactivex.disposables.CompositeDisposable
import io.reactivex.disposables.Disposable
import io.reactivex.observers.DisposableCompletableObserver
import io.reactivex.schedulers.Schedulers

/**
 * @author Kudzai Chasinda
 */
abstract class CompletableUseCase<in Params>
constructor(private val postExecutionThread: PostExecutionThread) {
    private val disposables = CompositeDisposable()

    protected abstract fun buildUseCaseCompletable(params: Params? = null): Completable

    open fun execute(observer: DisposableCompletableObserver, params: Params? = null) {
        val completable = this.buildUseCaseCompletable(params)
            .subscribeOn(Schedulers.io())
            .observeOn(postExecutionThread.scheduler)

        addDisposable(completable.subscribeWith(observer))

    }

    private fun addDisposable(disposable: Disposable) {
        disposables.add(disposable)
    }

    fun dispose() {
        disposables.dispose()
    }
}