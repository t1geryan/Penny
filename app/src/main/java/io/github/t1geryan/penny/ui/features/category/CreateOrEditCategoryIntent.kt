package io.github.t1geryan.penny.ui.features.category

import io.github.t1geryan.mvi.Intent

sealed interface CreateOrEditCategoryIntent : Intent {

    data class SetName(val name: String) : CreateOrEditCategoryIntent

    data object SaveCategory : CreateOrEditCategoryIntent

    data object NavigateUp : CreateOrEditCategoryIntent

    data object DismissDialog : CreateOrEditCategoryIntent
}
