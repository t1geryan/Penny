package io.github.t1geryan.domain.models

enum class Currency(
    val code: String,
    val symbol: String,
    val subUnitMultiplier: Float = 0.01f,
) {
    RUSSIAN_RUBLE("RUB", "\u20bd"),
    ;
}
