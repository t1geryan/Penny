package io.github.t1geryan.domain.usecases

import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.domain.repositories.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ObserveTransactionByIdUseCase {

    operator fun invoke(id: TransactionId): Flow<Transaction?>
}

class ObserveTransactionByIdUseCaseImpl @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) : ObserveTransactionByIdUseCase {

    override fun invoke(id: TransactionId): Flow<Transaction?> = transactionsRepository
        .observeTransactions()
        .map { transactions ->
            transactions.firstOrNull { it.id == id }
        }
}
