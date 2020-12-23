package com.chase.kudzie.chasemusic.images

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Suppress("FunctionName")
fun ImageScope(dispatcher: CoroutineDispatcher = Dispatchers.Unconfined): CoroutineScope =
    CoroutineScope(SupervisorJob() + dispatcher)