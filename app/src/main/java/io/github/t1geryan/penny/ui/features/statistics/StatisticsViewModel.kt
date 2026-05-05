package io.github.t1geryan.penny.ui.features.statistics

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.t1geryan.domain.usecases.ObserveCategoriesUseCase
import io.github.t1geryan.domain.usecases.ObserveTransactionsUseCase
import io.github.t1geryan.penny.ui.base.BaseViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import kotlinx.datetime.LocalDateRange
import javax.inject.Inject

@HiltViewModel
class StatisticsViewModel @Inject constructor(
    private val observeTransactionsUseCase: ObserveTransactionsUseCase,
    private val observeCategoriesUseCase: ObserveCategoriesUseCase,
) : BaseViewModel<StatisticsIntent, StatisticsState>(StatisticsState.initial()) {

    init {
        viewModelScope.launch {
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
            }
        }
    }

    override fun receiveIntent(intent: StatisticsIntent) = when (intent) {
        StatisticsIntent.DismissDialog -> invalidateDialog()
        StatisticsIntent.PickRange -> setDialog(StatisticsDialogState.RangePickerDialog)
        is StatisticsIntent.SetRange -> setRange(intent.range)
    }

    private fun setRange(range: LocalDateRange) {
        _state.update { it.copy(dateRange = range) }
    }

    private fun invalidateDialog() {
        setDialog(StatisticsDialogState.None)
    }

    private fun setDialog(dialogState: StatisticsDialogState) {
        _state.update { it.copy(dialogState = dialogState) }
    }
}
