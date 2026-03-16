package io.github.t1geryan.penny.ui.features.createtransaction

import androidx.lifecycle.viewModelScope
import dagger.assisted.Assisted
import dagger.assisted.AssistedFactory
import dagger.assisted.AssistedInject
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.domain.usecases.CreateOrUpdateTransactionUseCase
import io.github.t1geryan.domain.usecases.ObserveCategoriesUseCase
import io.github.t1geryan.domain.usecases.ObserveTransactionByIdUseCase
import io.github.t1geryan.domain.usecases.ValidateAmountUseCase
import io.github.t1geryan.penny.ui.base.BaseViewModel
import io.github.t1geryan.penny.ui.contracts.formatRaw
import io.github.t1geryan.penny.ui.features.createtransaction.CreateOrUpdateTransactionDialogState.CategoryPickerDialog
import io.github.t1geryan.penny.ui.features.createtransaction.CreateOrUpdateTransactionDialogState.DatePickerDialog
import io.github.t1geryan.penny.ui.features.createtransaction.CreateOrUpdateTransactionDialogState.TimePickerDialog
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateTime

@HiltViewModel(assistedFactory = CreateOrUpdateTransactionViewModel.Factory::class)
class CreateOrUpdateTransactionViewModel @AssistedInject constructor(
    @Assisted private val transactionId: TransactionId?,
    observeTransactionByIdUseCase: ObserveTransactionByIdUseCase,
    private val observeCategoriesUseCase: ObserveCategoriesUseCase,
    private val validateAmountUseCase: ValidateAmountUseCase,
    private val createOrUpdateTransactionUseCase: CreateOrUpdateTransactionUseCase,
) : BaseViewModel<CreateOrUpdateTransactionIntent, CreateOrUpdateTransactionState>(
    CreateOrUpdateTransactionState.initial(),
) {

    init {
        _state.update { it.copy(isEditing = transactionId != null) }
        transactionId?.let {
            setLoading(true)
            viewModelScope.launch {
                observeTransactionByIdUseCase(transactionId).first()?.let(::handleTransaction)
                setLoading(false)
            }
        }
    }

    override fun receiveIntent(intent: CreateOrUpdateTransactionIntent) = when (intent) {
        is CreateOrUpdateTransactionIntent.SetName -> setName(intent.name)
        is CreateOrUpdateTransactionIntent.SetAmount -> validateAndSetAmount(intent.enteredAmount)
        is CreateOrUpdateTransactionIntent.SetQuickAmount -> setQuickAmount(intent.amount)
        CreateOrUpdateTransactionIntent.PickCategory -> showCategoryPicker()
        CreateOrUpdateTransactionIntent.PickDate -> setDialog(
            DatePickerDialog(initialSelected = _state.value.selectedDate?.date),
        )
        CreateOrUpdateTransactionIntent.DismissDialog -> invalidateDialog()
        is CreateOrUpdateTransactionIntent.SetCategory -> setCategory(intent.category)
        is CreateOrUpdateTransactionIntent.SetDate -> setDate(intent.date)
        is CreateOrUpdateTransactionIntent.PickTime -> setDialog(
            TimePickerDialog(
                initialSelected = _state.value.selectedDate?.time,
                date = intent.date,
            ),
        )
        CreateOrUpdateTransactionIntent.SaveTransaction -> saveTransaction()
    }

    private fun saveTransaction() = _state.value.let { state ->
        if (state.selectedCategory == null || state.selectedDate == null) {
            return@let
        }
        setLoading(true)
        viewModelScope.launch {
            createOrUpdateTransactionUseCase(
                Transaction(
                    id = transactionId ?: 0,
                    name = state.enteredName,
                    amount = Amount(
                        value = state.enteredAmount.toFloat(),
                        currency = state.selectedCurrency,
                    ),
                    category = state.selectedCategory,
                    date = state.selectedDate,
                ),
            )
            setLoading(false)
        }
    }

    private fun setName(name: String) {
        _state.update { it.copy(enteredName = name, isNameValid = name.isNotBlank()) }
    }

    private fun validateAndSetAmount(enteredAmount: String) {
        _state.update { it.copy(enteredAmount = enteredAmount) }
        if (enteredAmount.isBlank()) {
            _state.update { it.copy(isAmountValid = false) }
            return
        }
        val parsed = enteredAmount.toFloatOrNull()
        if (parsed != null) {
            val currency = _state.value.selectedCurrency
            val amount = Amount(parsed, currency)
            _state.update {
                it.copy(
                    isAmountValid = validateAmountUseCase(amount),
                )
            }
        } else {
            _state.update {
                it.copy(isAmountValid = false)
            }
        }
    }

    private fun setQuickAmount(amount: Amount) {
        _state.update {
            it.copy(
                enteredAmount = amount.formatRaw(),
                isAmountValid = true,
            )
        }
    }

    private fun setCategory(category: Category) {
        _state.update { it.copy(selectedCategory = category) }
    }

    private fun setDate(date: LocalDateTime) {
        _state.update { it.copy(selectedDate = date) }
    }

    private fun setLoading(isLoading: Boolean) {
        _state.update { it.copy(isLoading = isLoading) }
    }

    private fun handleTransaction(transaction: Transaction) {
        _state.update {
            it.copy(
                enteredName = transaction.name,
                enteredAmount = transaction.amount.formatRaw(),
                selectedCurrency = transaction.amount.currency,
                selectedCategory = transaction.category,
                selectedDate = transaction.date,
            )
        }
    }

    private fun showCategoryPicker() {
        viewModelScope.launch {
            setDialog(
                CategoryPickerDialog(
                    categories = observeCategoriesUseCase().first(),
                    initialSelected = _state.value.selectedCategory,
                ),
            )
        }
    }

    private fun invalidateDialog() {
        setDialog(CreateOrUpdateTransactionDialogState.None)
    }

    private fun setDialog(dialog: CreateOrUpdateTransactionDialogState) {
        _state.update { it.copy(dialogState = dialog) }
    }

    @AssistedFactory
    interface Factory {
        fun create(
            transactionId: TransactionId?,
        ): CreateOrUpdateTransactionViewModel
    }
}
