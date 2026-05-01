package io.github.t1geryan.domain.usecases

import io.github.t1geryan.coroutines.runSuspendCatching
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.domain.repositories.TransactionsRepository
import javax.inject.Inject

interface CreateOrUpdateTransactionUseCase {

    suspend operator fun invoke(transaction: Transaction): Result<TransactionId>
}

class CreateOrUpdateTransactionUseCaseImpl @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) : CreateOrUpdateTransactionUseCase {

    override suspend fun invoke(transaction: Transaction): Result<TransactionId> = runSuspendCatching {
        if (transaction.id == 0L) {
            transactionsRepository.createTransaction(transaction)
        } else {
            transactionsRepository.updateTransaction(transaction)
            transaction.id
        }
    }
}
