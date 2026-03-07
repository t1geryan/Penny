package io.github.t1geryan.domain.exceptions

class ApiException(
    private val code: ApiErrorCode,
    message: String?,
) : AppException(message) {

    constructor(code: ApiErrorCode) : this(code, null)
}
