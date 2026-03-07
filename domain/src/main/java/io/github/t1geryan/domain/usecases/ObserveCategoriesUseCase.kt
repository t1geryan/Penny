package io.github.t1geryan.domain.usecases

import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.repositories.CategoriesRepository
import kotlinx.coroutines.flow.Flow
import javax.inject.Inject

interface ObserveCategoriesUseCase {
    operator fun invoke(): Flow<List<Category>>
}

class ObserveCategoriesUseCaseImpl @Inject constructor(
    private val categoriesRepository: CategoriesRepository,
) : ObserveCategoriesUseCase {
    override fun invoke(): Flow<List<Category>> = categoriesRepository.observeCategories()
}