package io.github.t1geryan.penny.ui.features.createtransaction

import io.github.t1geryan.mvi.Event

sealed interface CreateOrUpdateTransactionEvent : Event {

    data object NavigateUp : CreateOrUpdateTransactionEvent
}
