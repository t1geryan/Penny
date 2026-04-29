package io.github.t1geryan.penny.ui.features.category

import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.mvi.Intent

sealed interface CreateOrEditCategoryIntent : Intent {

    data class SetName(val name: String) : CreateOrEditCategoryIntent

    data class SetLimit(val limit: String) : CreateOrEditCategoryIntent

    data class SetColor(val color: Long) : CreateOrEditCategoryIntent

    data class SetEmoji(val emoji: String) : CreateOrEditCategoryIntent

    data class SetCurrency(val currency: Currency) : CreateOrEditCategoryIntent

    data object PickCurrency : CreateOrEditCategoryIntent

    data object SaveCategory : CreateOrEditCategoryIntent

    data object NavigateUp : CreateOrEditCategoryIntent

    data object DismissDialog : CreateOrEditCategoryIntent
}
