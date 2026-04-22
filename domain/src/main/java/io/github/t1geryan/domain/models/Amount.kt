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


    /**
     * Calculates the remaining amount after subtracting spent from limit.
     * @param limit The limit amount.
     * @param spent The spent amount.
     * @return The remaining amount.
     * @throws IllegalArgumentException if currencies do not match.
     */
    fun calculateRemaining(limit: Amount): Amount {
        require(limit.currency == currency) { "Currencies must match" }
        return Amount(limit.value - value, limit.currency)
    }

    /**
     * Calculates the ratio of spent to limit.
     * @param spent The spent amount.
     * @param limit The limit amount.
     * @return The ratio as a Float.
     * @throws IllegalArgumentException if currencies do not match.
     */
    fun calculateSpentToLimitRatio(limit: Amount): Float {
        require(currency == limit.currency) { "Currencies must match" }
        return value.toFloat() / limit.value.toFloat()
    }
}
