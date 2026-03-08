package io.github.t1geryan.penny.ui.contracts

import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.format.MonthNames
import kotlinx.datetime.format.Padding
import kotlinx.datetime.format.char

object OutputFormats {

    const val cost = "%.2f"

    fun fullDate(monthNames: MonthNames) = LocalDateTime.Format {
        monthName(monthNames)
        day(padding = Padding.NONE)
        char(',')
        year()
    }

    fun shortDate(monthNames: MonthNames) = LocalDateTime.Format {
        monthName(monthNames)
        day(padding = Padding.NONE)
    }
}
