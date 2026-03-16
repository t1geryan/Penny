package io.github.t1geryan.penny.ui.features.transactions

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.domain.usecases.DeleteTransactionByIdUseCase
import io.github.t1geryan.domain.usecases.ObserveCategoriesUseCase
import io.github.t1geryan.domain.usecases.ObserveTransactionsUseCase
import io.github.t1geryan.penny.ui.base.BaseViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateRange
import javax.inject.Inject

@HiltViewModel
class TransactionsViewModel @Inject constructor(
    private val observeTransactionsUseCase: ObserveTransactionsUseCase,
    private val observeCategoriesUseCase: ObserveCategoriesUseCase,
    private val deleteTransactionByIdUseCase: DeleteTransactionByIdUseCase,
) : BaseViewModel<TransactionsIntent, TransactionsState>(TransactionsState.initial()) {

    init {
        viewModelScope.launch {
            observeTransactionsUseCase().collect { transactions ->
                _state.update { it.copy(transactions = transactions) }
            }
        }
    }

    override fun receiveIntent(intent: TransactionsIntent) = when (intent) {
        TransactionsIntent.PickFiltrationRange -> setDialog(TransactionsDialogState.DateRangeFilterPicker)
        is TransactionsIntent.SetFiltrationRange -> setFiltrationRange(intent.range)
        TransactionsIntent.DismissDialog -> invalidateDialog()
        TransactionsIntent.PickFiltrationCategories -> showCategoriesPicker()
        is TransactionsIntent.SetFiltrationCategories -> setFiltrationCategories(intent.categories)
        is TransactionsIntent.ConfirmTransactionDelete -> deleteTransaction(intent.transactionId)
        is TransactionsIntent.DeleteTransaction -> setDialog(
            TransactionsDialogState.ConfirmTransactionDelete(intent.transactionId),
        )
        TransactionsIntent.ClearAllFilters -> clearAllFilters()
    }

    private fun clearAllFilters() {
        _state.update {
            it.copy(
                categoryFilter = emptyList(),
                dataRangeFilter = null,
            )
        }
    }

    private fun deleteTransaction(transactionId: TransactionId) {
        setLoading(true)
        viewModelScope.launch {
            deleteTransactionByIdUseCase(transactionId)
            setLoading(false)
        }
    }

    private fun setFiltrationCategories(categories: List<Category>) {
        _state.update { it.copy(categoryFilter = categories) }
    }

    private fun showCategoriesPicker() {
        viewModelScope.launch {
            setDialog(
                TransactionsDialogState.CategoryFilterPicker(
                    categories = observeCategoriesUseCase().first(),
                    initialSelectedCategories = _state.value.categoryFilter,
                ),
            )
        }
    }

    private fun setFiltrationRange(range: LocalDateRange?) {
        _state.update { it.copy(dataRangeFilter = range) }
        invalidateDialog()
    }

    private fun invalidateDialog() {
        setDialog(TransactionsDialogState.None)
    }

    private fun setDialog(dialogState: TransactionsDialogState) {
        _state.update { it.copy(dialogState = dialogState) }
    }

    private fun setLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }
}
