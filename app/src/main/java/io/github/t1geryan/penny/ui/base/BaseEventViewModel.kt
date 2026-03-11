package io.github.t1geryan.penny.ui.base

import androidx.lifecycle.viewModelScope
import io.github.t1geryan.mvi.Event
import io.github.t1geryan.mvi.EventSender
import io.github.t1geryan.mvi.Intent
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.channels.BufferOverflow
import kotlinx.coroutines.channels.Channel
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.receiveAsFlow
import kotlinx.coroutines.launch

abstract class BaseEventViewModel<I : Intent, S : Any, E : Event>(initialState: S) :
    BaseViewModel<I, S>(initialState),
    EventSender<E> {

    protected val _events = Channel<E>(Channel.UNLIMITED, BufferOverflow.DROP_LATEST)
    override val events: Flow<E> = _events.receiveAsFlow()

    protected fun sendEvent(event: E) {
        viewModelScope.launch(Dispatchers.Main.immediate) {
            _events.send(event)
        }
    }
}
