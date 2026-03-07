package io.github.t1geryan.domain.usecases

import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.domain.repositories.TransactionsRepository
import javax.inject.Inject

interface CreateOrUpdateCategoryUseCase {

    suspend operator fun invoke(category: Category): Result<CategoryId>
}

class CreateOrUpdateCategoryUseCaseImpl @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) : CreateOrUpdateCategoryUseCase {
    override suspend fun invoke(category: Category): Result<CategoryId> = runCatching {
        if (category.id == 0) {
            transactionsRepository.createCategory(category)
        } else {
            transactionsRepository.updateCategory(category)
            category.id
        }
    }
}
