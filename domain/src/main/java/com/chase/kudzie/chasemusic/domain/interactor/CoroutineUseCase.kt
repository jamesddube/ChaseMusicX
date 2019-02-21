package com.chase.kudzie.chasemusic.domain.interactor

import kotlinx.coroutines.*
import kotlin.coroutines.CoroutineContext

/**
 * @author Kudzai A Chasinda
 */
abstract class CoroutineUseCase<T, in Params> {

    private var parentJob: Job = Job()
    private var backgroundContext: CoroutineContext = Dispatchers.IO
    private var foregroundContext: CoroutineContext = Dispatchers.Main

    protected abstract suspend fun executeOnBackground(params: Params? = null): T

    fun execute(onComplete: (T) -> Unit, onError: (Throwable) -> Unit) {
        unsubscribe()
        parentJob = Job()
        CoroutineScope(foregroundContext + parentJob).launch {
            try {
                val result = withContext(backgroundContext) {
                    executeOnBackground()
                }
                onComplete.invoke(result)
            } catch (e: CancellationException) {
                //TODO handle case for cancellation Kudzie
            } catch (e: Exception) {
                onError(e)
            }
        }
    }

    protected suspend fun <X> background(
        context: CoroutineContext = backgroundContext,
        block: suspend () -> X
    ): Deferred<X> {
        return CoroutineScope(context + parentJob).async {
            block.invoke()
        }
    }

    private fun unsubscribe() {
        parentJob.apply {
            cancelChildren()
            cancel()
        }
    }

}