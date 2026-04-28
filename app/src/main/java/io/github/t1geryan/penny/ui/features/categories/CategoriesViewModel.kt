package io.github.t1geryan.penny.ui.features.categories

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.usecases.DeleteCategoryByIdUseCase
import io.github.t1geryan.domain.usecases.ObserveCategoriesUseCase
import io.github.t1geryan.domain.usecases.ObserveTransactionsUseCase
import io.github.t1geryan.penny.ui.base.BaseEventViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val observeCategoriesUseCase: ObserveCategoriesUseCase,
    private val observeTransactionsUseCase: ObserveTransactionsUseCase,
    private val deleteCategoryByIdUseCase: DeleteCategoryByIdUseCase,
) : BaseEventViewModel<CategoriesIntent, CategoriesState, CategoriesEvent>(CategoriesState.initial()) {

    init {
        viewModelScope.launch {
            combine(observeTransactionsUseCase(), observeCategoriesUseCase()) { transactions, categories ->
                Pair(transactions, categories)
            }.collect { (transactions, categories) ->
                _state.update {
                    it.copy(
                        transactions = transactions,
                        categories = categories,
                    )
                }
            }
            observeTransactionsUseCase().collect { transactions ->
                _state.update { it.copy(transactions = transactions) }
            }
        }
    }

    override fun receiveIntent(intent: CategoriesIntent) = when (intent) {
        is CategoriesIntent.DeleteCategory -> handleDeleteCategoryRequest(intent.category)
        is CategoriesIntent.ConfirmCategoryDelete -> deleteCategory(intent.category)
        CategoriesIntent.DismissDialog -> setDialog(CategoriesDialogState.None)
        is CategoriesIntent.NavigateToCreteOrEditCategory -> sendEvent(
            CategoriesEvent.NavigateToCreateOrEditCategory(intent.categoryId),
        )
    }

    private fun handleDeleteCategoryRequest(category: Category) {
        val categoryTransactions = state.value.transactions.filter { it.category.id == category.id }
        if (categoryTransactions.isEmpty()) {
            setDialog(CategoriesDialogState.DeleteCategoryConfirmation(category))
        } else {
            setDialog(CategoriesDialogState.CategoryWithDependenciesWarning(category))
        }
    }

    private fun deleteCategory(category: Category) {
        setLoading(true)
        viewModelScope.launch {
            deleteCategoryByIdUseCase(category.id)
            setLoading(false)
        }
    }

    private fun setDialog(dialogState: CategoriesDialogState) {
        _state.update { it.copy(dialogState = dialogState) }
    }

    private fun setLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }
}
