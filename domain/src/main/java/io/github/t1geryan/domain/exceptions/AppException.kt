package io.github.t1geryan.domain.exceptions

open class AppException(
    message: String?,
    cause: Throwable?,
) : Throwable(message, cause) {

    constructor(message: String?) : this(message, null)

    constructor(cause: Throwable?) : this(null, cause)

    constructor() : this(null, null)
}
