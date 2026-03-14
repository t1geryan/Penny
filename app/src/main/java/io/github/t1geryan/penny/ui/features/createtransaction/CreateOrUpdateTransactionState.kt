package io.github.t1geryan.penny.ui.features.createtransaction

import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.mvi.InitialStateProvider
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

data class CreateOrUpdateTransactionState(
    val name: String?,
    val amount: Amount,
    val category: Category?,
    val date: LocalDateTime,
) {

    companion object : InitialStateProvider<CreateOrUpdateTransactionState> {
        override fun initial(): CreateOrUpdateTransactionState {
            val current = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault())
            return CreateOrUpdateTransactionState(
                name = null,
                amount = Amount(0, Currency.US_DOLLAR),
                category = null,
                date = current,
            )
        }
    }
}
