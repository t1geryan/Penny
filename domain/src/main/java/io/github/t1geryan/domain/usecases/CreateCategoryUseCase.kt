package io.github.t1geryan.domain.usecases

import io.github.t1geryan.domain.models.Category

interface CreateCategoryUseCase {

    suspend operator fun invoke(category: Category)
}
