package io.github.t1geryan.penny.ui.features.category

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.domain.usecases.CreateOrUpdateCategoryUseCase
import io.github.t1geryan.domain.usecases.ObserveCategoryByIdUseCase
import io.github.t1geryan.penny.ui.base.BaseEventViewModel
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch

@HiltViewModel(assistedFactory = CreateOrEditCategoryViewModel.Factory::class)
class CreateOrEditCategoryViewModel @AssistedInject constructor(
    @Assisted private val categoryId: CategoryId?,
    observeCategoryByIdUseCase: ObserveCategoryByIdUseCase,
    private val createOrUpdateCategoryUseCase: CreateOrUpdateCategoryUseCase,
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
        CreateOrEditCategoryIntent.SaveCategory -> saveCategory()
        is CreateOrEditCategoryIntent.SetName -> setName(intent.name)
        is CreateOrEditCategoryIntent.SetColor -> setColor(intent.color)
        is CreateOrEditCategoryIntent.SetEmoji -> setEmoji(intent.emoji)
        is CreateOrEditCategoryIntent.SetCurrency -> setCurrency(intent.currency)
        CreateOrEditCategoryIntent.PickCurrency -> pickCurrency()
        CreateOrEditCategoryIntent.DismissDialog -> invalidateDialog()
    }

    private fun handleCategory(category: Category) {
        _state.update {
            it.copy(
                enteredName = category.name,
                selectedColor = category.color,
                selectedEmoji = category.emoji,
                selectedCurrency = category.currency,
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

    private fun setName(name: String) {
        _state.update { it.copy(enteredName = name, isNameValid = name.isNotBlank()) }
    }

    private fun setColor(color: Long) {
        _state.update { it.copy(selectedColor = color) }
    }

    private fun setEmoji(emoji: String) {
        _state.update { it.copy(selectedEmoji = emoji) }
    }

    private fun setCurrency(currency: Currency) {
        _state.update { it.copy(selectedCurrency = currency) }
    }

    private fun pickCurrency() {
        val currentCurrency = _state.value.selectedCurrency
        setDialog(CreateOrEditCategoryDialogState.SelectCurrencyDialog(currentCurrency))
    }

    private fun saveCategory() = _state.value.let { state ->
        if (state.selectedColor == null || state.selectedEmoji == null) {
            return@let
        }
        setLoading(true)
        viewModelScope.launch {
            createOrUpdateCategoryUseCase(
                Category(
                    id = categoryId ?: 0,
                    name = state.enteredName,
                    emoji = state.selectedEmoji,
                    color = state.selectedColor,
                    limit = null,
                    currency = state.selectedCurrency,
                ),
            )
            setLoading(false)
            sendEvent(CreateOrEditCategoryEvent.NavigateUp)
        }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            categoryId: CategoryId?,
        ): CreateOrEditCategoryViewModel
    }
}
