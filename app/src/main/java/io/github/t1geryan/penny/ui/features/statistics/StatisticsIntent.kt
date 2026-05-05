package io.github.t1geryan.penny.ui.features.statistics

import io.github.t1geryan.mvi.Intent
import kotlinx.datetime.LocalDateRange

sealed interface StatisticsIntent : Intent {
    data object PickRange : StatisticsIntent

    data object DismissDialog : StatisticsIntent

    data class SetRange(val range: LocalDateRange) : StatisticsIntent
}
