package io.github.t1geryan.domain.usecases

import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.domain.repositories.TransactionsRepository
import javax.inject.Inject

interface DeleteTransactionByIdUseCase {

    suspend operator fun invoke(id: TransactionId): Result<Unit>
}

class DeleteTransactionByIdUseCaseImpl @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) : DeleteTransactionByIdUseCase {
    override suspend fun invoke(id: TransactionId) = runCatching {
        transactionsRepository.deleteTransaction(id)
    }
}
