package com.example.livestream.ui.viewmodel

import kotlinx.coroutines.CoroutineScope
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.receiveAsFlow

class SingleChannelEvent<T> {

    private val channel = Channel<T>(capacity = 1, onBufferOverflow = BufferOverflow.DROP_OLDEST)
    val flow = channel.receiveAsFlow()

    suspend fun send(value: T) = channel.send(value)

    fun trySend(value: T) = channel.trySend(value)

    fun launchAndCollectFlow(
        coroutineScope: CoroutineScope,
        collector: suspend (T) -> Unit
    ) {
        flow.launchAndCollect(coroutineScope, collector)
    }
}