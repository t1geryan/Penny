package io.github.t1geryan.penny.ui.contracts

import android.content.Context
import io.github.t1geryan.penny.R
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateRange
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

fun LocalDateRange.format(context: Context): String {
    if (start >= endInclusive) return endInclusive.format(context)
    val fromFormated = start.format(context = context)
    val toFormatted = endInclusive.format(context = context)

    return context.getString(R.string.common_date_range, fromFormated, toFormatted)
}

fun LocalDateTime.format(context: Context): String = date.format(context)

fun LocalDate.format(context: Context): String {
    val currentYear = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
    return if (year == currentYear) {
        format(OutputFormats.shortDate(context.monthNames))
    } else {
        format(OutputFormats.fullDate(context.monthNames))
    }
}
