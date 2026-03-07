package io.github.t1geryan.mvi

/**
 * Interface that intent receivers implement specifying a specific intent class
 */
interface IntentReceiver<I : Intent> {

    fun receiveIntent(intent: I)
}
