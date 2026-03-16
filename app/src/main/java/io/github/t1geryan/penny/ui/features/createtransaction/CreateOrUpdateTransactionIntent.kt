package io.github.t1geryan.penny.ui.features.createtransaction

import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.mvi.Intent
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime

sealed interface CreateOrUpdateTransactionIntent : Intent {

    data class SetAmount(val enteredAmount: String) : CreateOrUpdateTransactionIntent

    data class SetQuickAmount(val amount: Amount) : CreateOrUpdateTransactionIntent

    data class SetName(val name: String) : CreateOrUpdateTransactionIntent

    data object PickCategory : CreateOrUpdateTransactionIntent

    data class SetCategory(val category: Category) : CreateOrUpdateTransactionIntent

    data object PickDate : CreateOrUpdateTransactionIntent

    data class PickTime(val date: LocalDate) : CreateOrUpdateTransactionIntent

    data class SetDate(val date: LocalDateTime) : CreateOrUpdateTransactionIntent

    data object DismissDialog : CreateOrUpdateTransactionIntent
}
