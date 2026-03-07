package io.github.t1geryan.domain.usecases

import io.github.t1geryan.domain.exceptions.CategoryHasRelationsException
import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.domain.repositories.TransactionsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface DeleteCategoryByIdUseCase {
    suspend operator fun invoke(id: CategoryId): Result<Unit>
}

class DeleteCategoryByIdUseCaseImpl @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) : DeleteCategoryByIdUseCase {
    override suspend fun invoke(id: CategoryId): Result<Unit> = runCatching {
        val relatedTransactions = transactionsRepository.observeTransactionsByCategory(id).first()
        if (relatedTransactions.isNotEmpty()) throw CategoryHasRelationsException(id)

        transactionsRepository.deleteCategoryById(id)
    }
}
