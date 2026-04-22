package io.github.t1geryan.penny.ui.features.categories

import androidx.lifecycle.viewModelScope
import dagger.hilt.android.lifecycle.HiltViewModel
import io.github.t1geryan.domain.usecases.ObserveCategoriesUseCase
import io.github.t1geryan.domain.usecases.ObserveTransactionsUseCase
import io.github.t1geryan.penny.ui.base.BaseViewModel
import kotlinx.coroutines.flow.combine
import kotlinx.coroutines.flow.update
import kotlinx.coroutines.launch
import javax.inject.Inject

@HiltViewModel
class CategoriesViewModel @Inject constructor(
    private val observeCategoriesUseCase: ObserveCategoriesUseCase,
    private val observeTransactionsUseCase: ObserveTransactionsUseCase,
) : BaseViewModel<CategoriesIntent, CategoriesState>(CategoriesState.initial()) {

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

    override fun receiveIntent(intent: CategoriesIntent) {
        // no intents for now
    }
}
