package io.github.t1geryan.domain.usecases

import io.github.t1geryan.domain.models.Category
import kotlinx.coroutines.flow.Flow

interface  ListenCategoriesUseCase {
    operator fun invoke(): Flow<List<Category>>
}
