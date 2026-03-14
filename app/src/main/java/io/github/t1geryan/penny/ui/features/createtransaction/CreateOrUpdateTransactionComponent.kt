package io.github.t1geryan.penny.ui.features.createtransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.models.Percent
import io.github.t1geryan.penny.R
import io.github.t1geryan.penny.ui.contracts.format
import io.github.t1geryan.penny.ui.views.core.ComponentWithTopBar
import io.github.t1geryan.penny.ui.views.core.DefaultBackButton
import io.github.t1geryan.penny.ui.views.spacing.Spacer
import io.github.t1geryan.theme.cornerRadius
import io.github.t1geryan.theme.spacing

@Composable
fun CreateOrUpdateTransactionComponent(
    state: CreateOrUpdateTransactionState,
    onSendIntent: (CreateOrUpdateTransactionIntent) -> Unit,
    onNavigateUp: () -> Unit,
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
            DefaultBackButton(onClick = onNavigateUp)
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
private fun Content(
    state: CreateOrUpdateTransactionState,
    onSendIntent: (CreateOrUpdateTransactionIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        modifier = modifier,
    ) {
        AmountCard(
            state = state,
            onSendIntent = onSendIntent,
            modifier = Modifier
                .fillMaxWidth()
                .padding(MaterialTheme.spacing.medium),
        )
    }
}

@Composable
private fun AmountCard(
    state: CreateOrUpdateTransactionState,
    onSendIntent: (CreateOrUpdateTransactionIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Card(
        modifier = modifier,
    ) {
        Text(stringResource(R.string.screen_create_or_update_transaction_fill_amount_title))
        Spacer(MaterialTheme.spacing.normal)
        TextField(
            value = state.enteredAmount,
            onValueChange = { onSendIntent },
        )
        if (state.isEditing.not()) {
            QuickAmounts(
                selectedCurrency = state.selectedCurrency,
                onAmountSelected = { /* TODO */ },
                modifier = Modifier.fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun QuickAmounts(
    selectedCurrency: Currency,
    onAmountSelected: (Amount) -> Unit,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
    ) {
        selectedCurrency.quickAmounts.forEach { amount ->
            Button(
                onClick = { onAmountSelected(amount) },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                shape = RoundedCornerShape(MaterialTheme.cornerRadius.large),
                modifier = Modifier
                    .widthIn(max = 48.dp)
                    .weight(Percent.WHOLE.fraction),
            ) {
                Text(amount.format())
            }

        }
    }
}
