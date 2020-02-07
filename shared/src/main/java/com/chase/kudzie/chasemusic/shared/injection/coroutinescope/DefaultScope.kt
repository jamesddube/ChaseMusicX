package com.chase.kudzie.chasemusic.shared.injection.coroutinescope

import kotlinx.coroutines.CoroutineDispatcher
import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.SupervisorJob

@Suppress("FunctionName")
fun DefaultScope(dispatcher: CoroutineDispatcher = Dispatchers.Default): CoroutineScope =
    CoroutineScope(SupervisorJob() + dispatcher)