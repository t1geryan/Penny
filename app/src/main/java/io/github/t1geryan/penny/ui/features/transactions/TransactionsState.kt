package io.github.t1geryan.penny.ui.features.transactions

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.Immutable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.mvi.InitialStateProvider
import io.github.t1geryan.penny.R
import io.github.t1geryan.theme.icons
import kotlinx.datetime.LocalDateRange

@Immutable
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

    val emptyState: TransactionsEmptyState?
        get() = when {
            transactions.isEmpty() -> TransactionsEmptyState.NO_TRANSACTIONS
            filteredTransactions.isEmpty() && categoryFilter.isNotEmpty() -> TransactionsEmptyState.NO_TRANSACTIONS_THIS_FILTER
            filteredTransactions.isEmpty() -> TransactionsEmptyState.NO_TRANSACTIONS_THIS_PERIOD
            else -> null
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

enum class TransactionsEmptyState {
    NO_TRANSACTIONS,
    NO_TRANSACTIONS_THIS_PERIOD,
    NO_TRANSACTIONS_THIS_FILTER,
    ;

    val icon: ImageVector
        @Composable
        @ReadOnlyComposable
        get() = when (this) {
            NO_TRANSACTIONS -> MaterialTheme.icons.add
            NO_TRANSACTIONS_THIS_PERIOD -> MaterialTheme.icons.calendar
            NO_TRANSACTIONS_THIS_FILTER -> MaterialTheme.icons.filter
        }

    val title: String
        @Composable
        @ReadOnlyComposable
        get() {
            val res = when (this) {
                NO_TRANSACTIONS -> R.string.screen_transactions_empty_no_transactions_title
                NO_TRANSACTIONS_THIS_PERIOD -> R.string.screen_transactions_empty_in_period_title
                NO_TRANSACTIONS_THIS_FILTER -> R.string.screen_transactions_empty_filtered_title
            }
            return stringResource(res)
        }

    val description: String
        @Composable
        @ReadOnlyComposable
        get() {
            val res = when (this) {
                NO_TRANSACTIONS -> R.string.screen_transactions_empty_no_transactions_description
                NO_TRANSACTIONS_THIS_PERIOD -> R.string.screen_transactions_empty_in_period_description
                NO_TRANSACTIONS_THIS_FILTER -> R.string.screen_transactions_empty_filtered_description
            }
            return stringResource(res)
        }

    val buttonTitle: String
        @Composable
        @ReadOnlyComposable
        get() {
            val res = when (this) {
                NO_TRANSACTIONS -> R.string.screen_transactions_empty_no_transactions_button
                NO_TRANSACTIONS_THIS_PERIOD -> R.string.screen_transactions_empty_in_period_button
                NO_TRANSACTIONS_THIS_FILTER -> R.string.screen_transactions_empty_filtered_button
            }
            return stringResource(res)
        }

    val buttonStyle: ButtonStyle
        get() = when (this) {
            NO_TRANSACTIONS -> ButtonStyle.Filled
            NO_TRANSACTIONS_THIS_PERIOD -> ButtonStyle.Filled
            NO_TRANSACTIONS_THIS_FILTER -> ButtonStyle.Outline
        }

    enum class ButtonStyle {
        Filled,
        Outline;
    }
}

sealed interface TransactionsDialogState {
    data object DateRangeFilterPicker : TransactionsDialogState

    data class CategoryFilterPicker(
        val categories: List<Category>,
        val initialSelectedCategories: List<Category>,
    ) : TransactionsDialogState

    data class ConfirmTransactionDelete(
        val transactionId: TransactionId,
    ) : TransactionsDialogState

    data object None : TransactionsDialogState
}
