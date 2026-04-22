package io.github.t1geryan.penny.ui.features.categories

import androidx.compose.runtime.Immutable
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.mvi.InitialStateProvider

@Immutable
data class CategoriesState(
    val categories: List<Category>,
    val transactions: List<Transaction>,
    val dialogState: CategoriesDialogState,
    val isLoading: Boolean,
) {

    val isEmpty: Boolean
        get() = categories.isEmpty()

    companion object : InitialStateProvider<CategoriesState> {
        override fun initial() = CategoriesState(
            categories = emptyList(),
            transactions = emptyList(),
            isLoading = false,
            dialogState = CategoriesDialogState.None,
        )
    }
}

sealed interface CategoriesDialogState {

    data class DeleteCategoryConfirmation(val category: Category) : CategoriesDialogState


    data class CategoryWithDependenciesWarning(val category: Category) : CategoriesDialogState

    data object None : CategoriesDialogState
}
