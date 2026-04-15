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

    companion object {
        val USER_LIST = listOf(
            US_DOLLAR,
            RUSSIAN_RUBLE,
        )
    }
}
