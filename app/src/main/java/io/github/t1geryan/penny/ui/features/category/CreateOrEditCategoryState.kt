package io.github.t1geryan.penny.ui.features.category

import androidx.compose.runtime.Immutable
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.mvi.InitialStateProvider

@Immutable
data class CreateOrEditCategoryState(
    val enteredName: String,
    val isNameValid: Boolean,
    val enteredLimit: String,
    val isLimitValid: Boolean,
    val selectedColor: Long?,
    val selectedEmoji: String?,
    val selectedCurrency: Currency,
    val isEditing: Boolean,
    val isLoading: Boolean,
    val dialogState: CreateOrEditCategoryDialogState,
) {

    val isAllowedToSave: Boolean
        get() = isNameValid && enteredName.isNotBlank() &&
                selectedColor != null && selectedEmoji != null

    companion object : InitialStateProvider<CreateOrEditCategoryState> {

        override fun initial() = CreateOrEditCategoryState(
            enteredName = "",
            isNameValid = true,
            enteredLimit = "",
            isLimitValid = true,
            selectedColor = null,
            selectedEmoji = null,
            selectedCurrency = Currency.US_DOLLAR,
            isEditing = false,
            isLoading = false,
            dialogState = CreateOrEditCategoryDialogState.None,
        )
    }
}

sealed interface CreateOrEditCategoryDialogState {

    data class SelectCurrencyDialog(val initialCurrency: Currency) : CreateOrEditCategoryDialogState

    data object None : CreateOrEditCategoryDialogState
}
