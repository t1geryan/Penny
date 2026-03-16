package io.github.t1geryan.penny.ui.utils

import kotlinx.datetime.LocalDate
import kotlinx.datetime.TimeZone
import kotlinx.datetime.atStartOfDayIn

fun LocalDate.toMillis(): Long =
    atStartOfDayIn(TimeZone.UTC).toEpochMilliseconds()
