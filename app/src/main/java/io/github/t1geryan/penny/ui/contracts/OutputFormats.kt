package io.github.t1geryan.penny.ui.contracts

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.DateTimeFormatBuilder
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char
import java.text.NumberFormat
import java.util.Locale

object OutputFormats {

    fun cost() = object : Formatter<Float> {

        private val numberFormat = NumberFormat.getNumberInstance(Locale.getDefault())

        override fun format(value: Float) = numberFormat.format(value)
    }

    fun fullDate(monthNames: MonthNames) = object : Formatter<LocalDate> {
        private val localDateFormat = LocalDate.Format {
            monthName(monthNames)
            space()
            day(padding = Padding.NONE)
            chars(", ")
            year()
        }

        override fun format(value: LocalDate) = localDateFormat.format(value)
    }

    fun shortDate(monthNames: MonthNames) = object : Formatter<LocalDate> {
        private val localDateFormat = LocalDate.Format {
            monthName(monthNames)
            space()
            day(padding = Padding.NONE)
        }

        override fun format(value: LocalDate) = localDateFormat.format(value)
    }

    interface Formatter<T : Any> {

        fun format(value: T): String
    }
}

private fun DateTimeFormatBuilder.space() {
    char(' ')
}
