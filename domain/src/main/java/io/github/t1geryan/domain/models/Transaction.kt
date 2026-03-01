package io.github.t1geryan.domain.models

import kotlinx.datetime.LocalDateTime

data class Transaction(
    val id: Int,
    val amount: Amount,
    val category: Category,
    val date: LocalDateTime,
)
