package io.github.t1geryan.domain.exceptions

class ConnectionException(message: String?, cause: Throwable?) : AppException(message, cause) {

    constructor(message: String?) : this(message, null)

    constructor(cause: Throwable?) : this(null, cause)

    constructor() : this(null, null)
}