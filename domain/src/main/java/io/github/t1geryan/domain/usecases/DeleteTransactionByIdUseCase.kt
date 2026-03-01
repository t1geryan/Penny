package io.github.t1geryan.domain.usecases

import io.github.t1geryan.domain.models.TransactionId

interface DeleteTransactionByIdUseCase {

    suspend operator fun invoke(id: TransactionId)
}
