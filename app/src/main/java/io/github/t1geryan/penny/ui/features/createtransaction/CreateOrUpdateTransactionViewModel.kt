package io.github.t1geryan.penny.ui.features.createtransaction

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.domain.usecases.ObserveTransactionByIdUseCase
import io.github.t1geryan.penny.ui.base.BaseViewModel
import io.github.t1geryan.penny.ui.contracts.formatNoCurrency
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CreateOrUpdateTransactionViewModel.Factory::class)
class CreateOrUpdateTransactionViewModel @AssistedInject constructor(
    @Assisted private val transactionId: TransactionId?,
    observeTransactionByIdUseCase: ObserveTransactionByIdUseCase,
) : BaseViewModel<CreateOrUpdateTransactionIntent, CreateOrUpdateTransactionState>(
    CreateOrUpdateTransactionState.initial(),
) {

    init {
        _state.update { it.copy(isEditing = transactionId != null) }
        transactionId?.let {
            setLoading(true)
            viewModelScope.launch {
                observeTransactionByIdUseCase(transactionId).first()?.let(::handleTransaction)
                setLoading(false)
            }
        }
    }

    override fun receiveIntent(intent: CreateOrUpdateTransactionIntent) = when (intent) {
        is CreateOrUpdateTransactionIntent.SetName -> TODO()
    }

    private fun setLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    private fun handleTransaction(transaction: Transaction) {
        _state.update {
            it.copy(
                enteredName = transaction.name,
                enteredAmount = transaction.amount.formatNoCurrency(),
                selectedCurrency = transaction.amount.currency,
                selectedCategory = transaction.category,
                selectedDate = transaction.date,
            )
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            transactionId: TransactionId?,
        ): CreateOrUpdateTransactionViewModel
    }
}
