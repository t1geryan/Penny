package io.github.t1geryan.domain.models

import androidx.compose.runtime.Immutable

@Immutable
enum class Currency(
    val code: String,
    val symbol: String,
    val subUnitMultiplier: Float = 0.01f,
) {
    US_DOLLAR("USD", "\u0024"),
    RUSSIAN_RUBLE("RUB", "\u20bd"),
    ;

    @Suppress("MagicNumber")
    val quickAmounts: List<Amount>
        get() = when (this) {
            US_DOLLAR -> listOf(10, 20, 50, 100)

            RUSSIAN_RUBLE -> listOf(100, 500, 1000, 5000)
        }.map { Amount((it / subUnitMultiplier).toInt(), currency = this) }
}
