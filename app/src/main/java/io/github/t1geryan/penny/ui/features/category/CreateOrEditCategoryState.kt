package io.github.t1geryan.penny.ui.features.category

import androidx.compose.runtime.Immutable
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.mvi.InitialStateProvider

@Immutable
data class CreateOrEditCategoryState(
    val enteredName: String,
    val isEditing: Boolean,
    val isLoading: Boolean,
    val dialogState: CreateOrEditCategoryDialogState,
) {

    companion object : InitialStateProvider<CreateOrEditCategoryState> {

        override fun initial() = CreateOrEditCategoryState(
            enteredName = "",
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
