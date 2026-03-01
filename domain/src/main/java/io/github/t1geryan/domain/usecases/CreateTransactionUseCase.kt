package io.github.t1geryan.domain.usecases

import io.github.t1geryan.domain.models.Transaction

interface CreateTransactionUseCase {

    suspend operator fun invoke(transaction: Transaction)
}
