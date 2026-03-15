package io.github.t1geryan.penny.ui.features.createtransaction

import androidx.compose.runtime.Immutable
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.mvi.InitialStateProvider
import kotlinx.datetime.LocalDateTime

@Immutable
data class CreateOrUpdateTransactionState(
    val enteredName: String,
    val isNameValid: Boolean,
    val enteredAmount: String,
    val isAmountValid: Boolean,
    val selectedCurrency: Currency,
    val selectedCategory: Category?,
    val selectedDate: LocalDateTime?,
    val isEditing: Boolean,
    val isLoading: Boolean,
    val dialogState: CreateOrUpdateTransactionDialogState,
) {

    companion object : InitialStateProvider<CreateOrUpdateTransactionState> {
        override fun initial(): CreateOrUpdateTransactionState = CreateOrUpdateTransactionState(
            enteredName = "",
            isNameValid = true,
            enteredAmount = "0.00",
            isAmountValid = true,
            selectedCurrency = Currency.US_DOLLAR,
            selectedCategory = null,
            selectedDate = null,
            isEditing = false,
            isLoading = false,
            dialogState = CreateOrUpdateTransactionDialogState.None,
        )
    }
}

sealed interface CreateOrUpdateTransactionDialogState {
    data object None : CreateOrUpdateTransactionDialogState

    data class CategoryPickerDialog(
        val categories: List<Category>,
        val initialSelected: Category?,
    ) : CreateOrUpdateTransactionDialogState

    data class DatePickerDialog(val initialSelected: LocalDateTime?) :
        CreateOrUpdateTransactionDialogState
}
