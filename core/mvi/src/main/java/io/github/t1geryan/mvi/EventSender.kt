package io.github.t1geryan.mvi

import kotlinx.coroutines.flow.Flow

/**
 * Interface to be implemented by classes which are sending events
 */
interface EventSender<E : Event> {

    val events: Flow<E>
}
