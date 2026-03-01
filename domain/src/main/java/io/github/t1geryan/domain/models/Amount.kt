package io.github.t1geryan.domain.models

/**
 * @property value - amount value in minimal subunit
 */
data class Amount(
    val value: Int,
    val currency: Currency,
)
