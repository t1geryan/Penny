package io.github.t1geryan.domain.models

import androidx.compose.runtime.Immutable

typealias CategoryId = Long

/**
 * @property name - human friendly name of Category
 * @property color - hex value of Category primary color
 */
@Immutable
data class Category(
    val id: CategoryId = 0L,
    val name: String,
    val emoji: String,
    val color: Long,
    val limit: Amount?,
    val currency: Currency,
)

fun Category.calculateSpentAmount(transactions: List<Transaction>): Amount {
    val categoryTransactions = transactions.filter { it.category.id == this.id }
    val categorySpent = categoryTransactions.fold(0) { sum, transaction ->
        sum + transaction.amount.value
    }
    return Amount(categorySpent, currency)
}
