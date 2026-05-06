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

    operator fun plus(other: Amount): Amount {
        requireCurrenciesMustMatch(other.currency)
        return Amount(value + other.value, currency)
    }

    /**
     * Calculates the remaining amount after subtracting spent from limit.
     * @param limit The limit amount.
     * @return The remaining amount.
     * @throws IllegalArgumentException if currencies do not match.
     */
    fun calculateRemaining(limit: Amount): Amount {
        requireCurrenciesMustMatch(limit.currency)
        return Amount((limit.value - value).coerceAtLeast(0), limit.currency)
    }

    /**
     * Calculates the ratio of spent to limit.
     * @param limit The limit amount.
     * @return The ratio as a Float.
     * @throws IllegalArgumentException if currencies do not match.
     */
    fun calculateSpentToLimitRatio(limit: Amount): Float {
        requireCurrenciesMustMatch(limit.currency)
        return value.toFloat() / limit.value.toFloat()
    }

    fun isLimitExceed(limit: Amount): Boolean {
        requireCurrenciesMustMatch(limit.currency)
        return value > limit.value
    }

    private fun requireCurrenciesMustMatch(other: Currency) {
        require(currency == other) { "Currencies must match" }
    }
}
