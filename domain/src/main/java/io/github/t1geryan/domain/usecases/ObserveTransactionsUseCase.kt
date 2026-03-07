package io.github.t1geryan.domain.usecases

import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.domain.repositories.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveTransactionsUseCase {
    operator fun invoke(): Flow<List<Transaction>>
}

class ObserveTransactionsUseCaseImpl @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) : ObserveTransactionsUseCase {
    override fun invoke(): Flow<List<Transaction>> = transactionsRepository.observeTransactions()
}