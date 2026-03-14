package io.github.t1geryan.models

import androidx.compose.runtime.Immutable

@Immutable
data class Percent(private val rawValue: Float) {

    val percents: Float
        get() = rawValue

    val fraction: Float
        get() = rawValue / FRACTION_MULTIPLIER

    val formatted: String
        get() = "$percents%"

    override fun toString(): String = formatted

    companion object {
        private const val FRACTION_MULTIPLIER = 100

        val NONE = Percent(0f)
        val QUARTER = Percent(25f)
        val THIRD = Percent(33f)
        val HALF = Percent(50f)
        val TWO_THIRDS = Percent(66f)
        val THREE_QUARTERS = Percent(75f)
        val WHOLE = Percent(100f)
    }
}
