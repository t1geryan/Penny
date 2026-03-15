package io.github.t1geryan.penny.ui.features.createtransaction

import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.mvi.Intent

sealed interface CreateOrUpdateTransactionIntent : Intent {

    data class SetAmount(val enteredAmount: String) : CreateOrUpdateTransactionIntent

    data class SetQuickAmount(val amount: Amount) : CreateOrUpdateTransactionIntent

    data class SetName(val name: String) : CreateOrUpdateTransactionIntent
}
