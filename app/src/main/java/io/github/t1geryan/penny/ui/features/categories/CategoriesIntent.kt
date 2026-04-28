package io.github.t1geryan.penny.ui.features.categories

import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.mvi.Intent

sealed interface CategoriesIntent : Intent {

    data class DeleteCategory(val category: Category) : CategoriesIntent

    data class ConfirmCategoryDelete(val category: Category) : CategoriesIntent

    data object DismissDialog : CategoriesIntent

    data class NavigateToCreteOrEditCategory(val categoryId: CategoryId? = null) : CategoriesIntent
}
