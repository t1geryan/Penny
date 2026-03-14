package io.github.t1geryan.penny.ui.features.createtransaction

import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.t1geryan.penny.R
import io.github.t1geryan.penny.ui.views.core.ComponentWithTopBar
import io.github.t1geryan.penny.ui.views.core.DefaultBackButton
import io.github.t1geryan.theme.spacing

@Composable
fun CreateOrUpdateTransactionComponent(
    state: CreateOrUpdateTransactionState,
    onSendIntent: (CreateOrUpdateTransactionIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    ComponentWithTopBar(
        title = stringResource(
            if (state.isEditing) {
                R.string.screen_create_or_update_transaction_update_title
            } else {
                R.string.screen_create_or_update_transaction_create_title
            },
        ),
        backButton = {
            DefaultBackButton(onClick = { onSendIntent(CreateOrUpdateTransactionIntent.NavigateUp) })
        },
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Content(
                state = state,
                onSendIntent = onSendIntent,
                modifier = Modifier.fillMaxSize(),
            )

            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }
}

@Composable
fun Content(
    state: CreateOrUpdateTransactionState,
    onSendIntent: (CreateOrUpdateTransactionIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        AmountCard(
            enteredAmount = state.enteredAmount,
            onSendIntent = onSendIntent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
        )
    }
}

@Composable
fun AmountCard(
    enteredAmount: String,
    onSendIntent: (CreateOrUpdateTransactionIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    TextField(
        value = enteredAmount,
        onValueChange = { onSendIntent },
        modifier = modifier,
    )
}
