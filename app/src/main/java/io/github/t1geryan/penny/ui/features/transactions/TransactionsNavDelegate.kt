package io.github.t1geryan.penny.ui.features.transactions

import io.github.t1geryan.domain.models.TransactionId

interface TransactionsNavDelegate {

    fun navigateToCreateOrEdit(transactionId: TransactionId?)
}
