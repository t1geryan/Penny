package io.github.t1geryan.penny.ui.features.statistics

import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.mvi.InitialStateProvider
import kotlinx.datetime.DatePeriod
import kotlinx.datetime.LocalDateRange
import kotlinx.datetime.TimeZone
import kotlinx.datetime.minus
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class StatisticsState(
    val dateRange: LocalDateRange,
    private val transactions: List<Transaction>,
    val categories: List<Category>,
    val dialogState: StatisticsDialogState,
) {

    val filteredTransactions: List<Transaction>
        get() = transactions.filter { transaction ->
            dateRange.contains(transaction.date.date)
        }

    val emptyState: StatisticsEmptyState
        get() = when {
            categories.isEmpty() -> StatisticsEmptyState.NoCategories
            transactions.isEmpty() -> StatisticsEmptyState.NoTransactions
            filteredTransactions.isEmpty() -> StatisticsEmptyState.NoTransactionsInRange
            else -> StatisticsEmptyState.NotEmpty
        }

    companion object : InitialStateProvider<StatisticsState> {
        override fun initial(): StatisticsState {
            val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
            return StatisticsState(
                dateRange = LocalDateRange(today - DatePeriod(months = 1), today),
                transactions = emptyList(),
                categories = emptyList(),
                dialogState = StatisticsDialogState.None,
            )
        }
    }
}

sealed interface StatisticsEmptyState {

    data object NotEmpty : StatisticsEmptyState

    data object NoTransactions : StatisticsEmptyState

    data object NoCategories : StatisticsEmptyState

    data object NoTransactionsInRange : StatisticsEmptyState
}

sealed interface StatisticsDialogState {

    data object RangePickerDialog : StatisticsDialogState

    data object None : StatisticsDialogState
}
