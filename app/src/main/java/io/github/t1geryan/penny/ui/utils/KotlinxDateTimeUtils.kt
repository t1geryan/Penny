package io.github.t1geryan.penny.ui.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

fun LocalDate.toMillis(timeZone: TimeZone = TimeZone.UTC): Long =
    atStartOfDayIn(timeZone).toEpochMilliseconds()
