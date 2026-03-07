package io.github.t1geryan.domain.models

import kotlinx.datetime.LocalDateTime

typealias TransactionId = Int

data class Transaction(
    val id: TransactionId = 0,
    val name: String,
    val amount: Amount,
    val category: Category,
    val date: LocalDateTime,
)
