package io.github.t1geryan.domain.models

import androidx.compose.runtime.Immutable
import kotlinx.datetime.LocalDate
import kotlinx.datetime.LocalDateRange
import kotlinx.datetime.LocalDateTime
import kotlinx.datetime.TimeZone
import kotlinx.datetime.toLocalDateTime
import kotlin.time.Clock

typealias TransactionId = Long

@Immutable
data class Transaction(
    val id: TransactionId = 0L,
    val name: String,
    val amount: Amount,
    val category: Category,
    val date: LocalDateTime,
)


val List<Transaction>.thisMonthTransactions: List<Transaction>
    get() {
        val today = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).date
        val monthStart = LocalDate(today.year, today.month, 1)
        val lastMonthRange = LocalDateRange(monthStart, today)
        return this.filter { transaction ->
            lastMonthRange.contains(transaction.date.date)
        }
    }
