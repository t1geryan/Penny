package io.github.t1geryan.penny.ui.features.createtransaction

import io.github.t1geryan.mvi.Intent

sealed interface CreateOrUpdateTransactionIntent : Intent {

    data class SetName(val name: String) : CreateOrUpdateTransactionIntent
}
