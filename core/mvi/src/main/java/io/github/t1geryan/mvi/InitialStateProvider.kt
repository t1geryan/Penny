package io.github.t1geryan.mvi

/**
 * Interface to be implemented by companion objects of state classes that require state creation for initial state
 * @param S state class type
 */
interface InitialStateProvider<S> {

    fun initial(): S
}
