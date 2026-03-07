package io.github.t1geryan.domain.repositories

import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.CategoryId
import kotlinx.coroutines.flow.Flow

interface CategoriesRepository {

    fun observeCategories(): Flow<List<Category>>

    fun observeCategoryById(id: CategoryId): Flow<Category?>

    suspend fun createCategory(category: Category): CategoryId

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategory(id: CategoryId)
}