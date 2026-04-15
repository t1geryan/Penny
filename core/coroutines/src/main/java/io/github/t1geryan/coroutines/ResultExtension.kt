package io.github.t1geryan.coroutines

import kotlin.coroutines.cancellation.CancellationException

@Suppress("TooGenericExceptionCaught")
suspend inline fun <R> runSuspendCatching(block: suspend () -> R): Result<R> {
    return try {
        Result.success(block())
    } catch (cancellationException: CancellationException) {
        throw cancellationException
    } catch (cause: Throwable) {
        Result.failure(cause)
    }
}
