package io.github.t1geryan.penny.ui.features.categories

import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.mvi.Event

interface CategoriesEvent : Event {

    data class NavigateToCreateOrEditCategory(val categoryId: CategoryId?) : CategoriesEvent
}
