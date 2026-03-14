package io.github.t1geryan.penny.ui.features.createtransaction

import androidx.compose.runtime.Immutable
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.mvi.InitialStateProvider
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

@Immutable
data class CreateOrUpdateTransactionState(
    val enteredName: String?,
    val enteredAmount: String,
    val selectedCurrency: Currency,
    val selectedCategory: Category?,
    val selectedDate: LocalDateTime,
    val isEditing: Boolean,
    val isLoading: Boolean,
) {

    companion object : InitialStateProvider<CreateOrUpdateTransactionState> {
        override fun initial(): CreateOrUpdateTransactionState {
            val current = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            return CreateOrUpdateTransactionState(
                enteredName = null,
                enteredAmount = "0.00",
                selectedCurrency = Currency.US_DOLLAR,
                selectedCategory = null,
                selectedDate = current,
                isEditing = false,
                isLoading = false,
            )
        }
    }
}
