package io.github.t1geryan.penny.ui.features.transactions

import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.mvi.Intent
import kotlinx.datetime.LocalDateRange

sealed interface TransactionsIntent : Intent {

    data object AddTransaction : TransactionsIntent

    data class EditTransaction(val id: TransactionId) : TransactionsIntent

    data object PickFiltrationRange : TransactionsIntent

    data class SetFiltrationRange(val range: LocalDateRange?) : TransactionsIntent

    data object DismissDialog : TransactionsIntent

    data object PickFiltrationCategories : TransactionsIntent

    data class SetFiltrationCategories(val categories: List<Category>) : TransactionsIntent

    data class DeleteTransaction(val transactionId: TransactionId) : TransactionsIntent

    data class ConfirmTransactionDelete(val transactionId: TransactionId) : TransactionsIntent

    data object ClearAllFilters : TransactionsIntent
}
