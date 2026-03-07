package io.github.t1geryan.domain.usecases

import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.domain.repositories.TransactionsRepository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

interface ObserveCategoryByIdUseCase {
    operator fun invoke(id: CategoryId): Flow<Category?>
}

class ObserveCategoryByIdUseCaseImpl @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
) : ObserveCategoryByIdUseCase {
    override fun invoke(id: CategoryId): Flow<Category?> = transactionsRepository
        .observeCategories()
        .map { categories ->
            categories.firstOrNull { it.id == id }
        }
}
