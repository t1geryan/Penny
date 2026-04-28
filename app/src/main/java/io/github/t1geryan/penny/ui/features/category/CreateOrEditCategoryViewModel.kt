package io.github.t1geryan.penny.ui.features.category

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.domain.usecases.ObserveCategoryByIdUseCase
import io.github.t1geryan.penny.ui.base.BaseEventViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CreateOrEditCategoryViewModel.Factory::class)
class CreateOrEditCategoryViewModel @AssistedInject constructor(
    @Assisted private val categoryId: CategoryId?,
    observeCategoryByIdUseCase: ObserveCategoryByIdUseCase,
) : BaseEventViewModel<CreateOrEditCategoryIntent, CreateOrEditCategoryState, CreateOrEditCategoryEvent>(
    CreateOrEditCategoryState.initial(),
) {

    init {
        _state.update { it.copy(isEditing = categoryId != null) }
        categoryId?.let {
            setLoading(true)
            viewModelScope.launch {
                observeCategoryByIdUseCase(categoryId).first()?.let(::handleCategory)
                setLoading(false)
            }
        }
    }

    override fun receiveIntent(intent: CreateOrEditCategoryIntent) = when (intent) {
        CreateOrEditCategoryIntent.NavigateUp -> sendEvent(CreateOrEditCategoryEvent.NavigateUp)
        CreateOrEditCategoryIntent.SaveCategory -> TODO()
        is CreateOrEditCategoryIntent.SetName -> TODO()
        CreateOrEditCategoryIntent.DismissDialog -> invalidateDialog()
    }

    private fun handleCategory(category: Category) {
        _state.update {
            it.copy(
                enteredName = category.name,
            )
        }
    }

    private fun setLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    private fun invalidateDialog() {
        setDialog(CreateOrEditCategoryDialogState.None)
    }

    private fun setDialog(dialog: CreateOrEditCategoryDialogState) {
        _state.update { it.copy(dialogState = dialog) }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            categoryId: CategoryId?,
        ): CreateOrEditCategoryViewModel
    }
}
