package io.github.t1geryan.penny.ui.features.createtransaction

import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier

@Composable
fun CreateOrUpdateTransactionComponent(
    state: CreateOrUpdateTransactionState,
    onSendIntent: (CreateOrUpdateTransactionIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    // TODO: DELETE
    modifier; onSendIntent
    state.name?.let {
        Text(it)
    }
}
