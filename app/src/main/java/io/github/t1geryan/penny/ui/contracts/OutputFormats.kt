package io.github.t1geryan.penny.ui.contracts

import kotlinx.datetime.LocalDate
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char

object OutputFormats {

    const val cost = "%.2f"

    fun fullDate(monthNames: MonthNames) = LocalDate.Format {
        monthName(monthNames)
        day(padding = Padding.NONE)
        char(',')
        year()
    }

    fun shortDate(monthNames: MonthNames) = LocalDate.Format {
        monthName(monthNames)
        day(padding = Padding.NONE)
    }
}
