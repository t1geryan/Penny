package io.github.t1geryan.domain.usecases

import io.github.t1geryan.domain.models.Transaction
import kotlinx.coroutines.flow.Flow

interface ListenTransactionsUseCase {
    operator fun invoke(): Flow<List<Transaction>>
}
