package io.github.t1geryan.penny.ui.features.createtransaction

import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.domain.usecases.ObserveTransactionByIdUseCase
import io.github.t1geryan.penny.ui.base.BaseViewModel
import kotlinx.coroutines.flow.update

@HiltViewModel(assistedFactory = CreateOrUpdateTransactionViewModel.Factory::class)
class CreateOrUpdateTransactionViewModel @AssistedInject constructor(
    @Assisted private var transactionId: TransactionId?,
    observeTransactionByIdUseCase: ObserveTransactionByIdUseCase,
) : BaseViewModel<CreateOrUpdateTransactionIntent, CreateOrUpdateTransactionState>(
    CreateOrUpdateTransactionState.initial(),
) {

    init {
        // TODO: DELETE
        observeTransactionByIdUseCase
        _state.update { it.copy(name = transactionId.toString()) }
    }

    override fun receiveIntent(intent: CreateOrUpdateTransactionIntent) = when (intent) {
        is CreateOrUpdateTransactionIntent.SetName -> TODO()
    }

    @AssistedFactory
    interface Factory {
        fun create(transactionId: TransactionId?): CreateOrUpdateTransactionViewModel
    }
}
