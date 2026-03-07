package io.github.t1geryan.penny.ui.base

import androidx.lifecycle.ViewModel
import io.github.t1geryan.mvi.Intent
import io.github.t1geryan.mvi.IntentReceiver
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.StateFlow
import kotlinx.coroutines.flow.asStateFlow

abstract class BaseViewModel<I : Intent, S : Any>(initialState: S) : ViewModel(),
    IntentReceiver<I> {

    protected val _state = MutableStateFlow(initialState)
    val state: StateFlow<S> = _state.asStateFlow()
}
