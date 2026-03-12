package io.github.t1geryan.penny.ui.features.transactions

import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.mvi.InitialStateProvider
import kotlinx.datetime.LocalDateRange

data class TransactionsState(
    val transactions: List<Transaction>,
    val dataRangeFilter: LocalDateRange?,
    val categoryFilter: List<Category>,
    val dialogState: TransactionsDialogState,
) {

    val filteredTransactions: List<Transaction>
        get() = transactions.filter { transaction ->
            val inRange = dataRangeFilter?.contains(transaction.date.date) ?: true
            val inCategories = categoryFilter
                .takeIf { it.isNotEmpty() }
                ?.contains(transaction.category)
                ?: true

            inRange && inCategories
        }

    companion object : InitialStateProvider<TransactionsState> {

        override fun initial(): TransactionsState = TransactionsState(
            transactions = emptyList(),
            dataRangeFilter = null,
            categoryFilter = emptyList(),
            dialogState = TransactionsDialogState.None,
        )
    }
}

sealed interface TransactionsDialogState {
    data object DateRangeFilterPicker : TransactionsDialogState

    data class CategoryFilterPicker(
        val categories: List<Category>,
        val initialSelectedCategories: List<Category>,
    ) : TransactionsDialogState

    data object None : TransactionsDialogState
}
