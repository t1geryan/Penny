package io.github.t1geryan.penny.ui.features.createtransaction

import androidx.compose.runtime.Immutable
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.mvi.InitialStateProvider
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.LocalTime

@Immutable
data class CreateOrUpdateTransactionState(
    val enteredName: String,
    val isNameValid: Boolean,
    val enteredAmount: String,
    val isAmountValid: Boolean,
    val selectedCategory: Category?,
    val selectedDate: LocalDateTime?,
    val isEditing: Boolean,
    val isLoading: Boolean,
    val dialogState: CreateOrUpdateTransactionDialogState,
) {

    val selectedCurrency: Currency?
        get() = selectedCategory?.currency

    val isAllowedToSave: Boolean
        get() = dialogState == CreateOrUpdateTransactionDialogState.None &&
                isLoading.not() &&
                isNameValid && isAmountValid && selectedCategory != null && selectedDate != null

    companion object : InitialStateProvider<CreateOrUpdateTransactionState> {
        override fun initial(): CreateOrUpdateTransactionState = CreateOrUpdateTransactionState(
            enteredName = "",
            isNameValid = true,
            enteredAmount = "",
            isAmountValid = true,
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

    data class DatePickerDialog(
        val initialSelected: LocalDate?,
    ) : CreateOrUpdateTransactionDialogState

    data class TimePickerDialog(
        val initialSelected: LocalTime?,
        val date: LocalDate,
    ) : CreateOrUpdateTransactionDialogState
}
