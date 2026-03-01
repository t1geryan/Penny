package io.github.t1geryan.domain.models

enum class Currency(val code: String, val subUnitMultiplier: Float = 0.01f) {
    RUSSIAN_RUBLE("RUB"),
    ;
}
