package io.github.t1geryan.domain.usecases

import io.github.t1geryan.domain.repositories.TransactionsRepository
import javax.inject.Inject

interface SyncUseCase {

    suspend operator fun invoke(): Result<Unit>
}

class SyncUseCaseImpl @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) : SyncUseCase {

    override suspend fun invoke(): Result<Unit> = runCatching {
        transactionsRepository.syncTransactions()
    }
}
