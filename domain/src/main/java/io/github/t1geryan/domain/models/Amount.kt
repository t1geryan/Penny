package io.github.t1geryan.domain.models

import androidx.compose.runtime.Immutable

/**
 * @property value - amount value in minimal subunit
 */
@Immutable
data class Amount(
    val value: Int,
    val currency: Currency,
) {

    constructor(
        value: Float,
        currency: Currency,
    ) : this((value / currency.subUnitMultiplier).toInt(), currency)

    val valueInCurrency: Float
        get() = value * currency.subUnitMultiplier
}
