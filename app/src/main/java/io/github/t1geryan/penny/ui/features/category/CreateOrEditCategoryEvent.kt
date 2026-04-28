package io.github.t1geryan.penny.ui.features.category

import io.github.t1geryan.mvi.Event

sealed interface CreateOrEditCategoryEvent : Event {

    data object NavigateUp : CreateOrEditCategoryEvent
}
