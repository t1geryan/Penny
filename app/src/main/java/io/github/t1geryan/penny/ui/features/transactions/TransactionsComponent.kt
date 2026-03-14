package io.github.t1geryan.penny.ui.features.transactions

import android.annotation.SuppressLint
import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedButton
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.models.Percent
import io.github.t1geryan.penny.R
import io.github.t1geryan.penny.ui.contracts.format
import io.github.t1geryan.penny.ui.utils.LocalFab
import io.github.t1geryan.penny.ui.views.core.ComponentWithTopBar
import io.github.t1geryan.penny.ui.views.dialog.ConfirmationDialog
import io.github.t1geryan.penny.ui.views.picker.CategoriesPicker
import io.github.t1geryan.penny.ui.views.picker.PennyDateRangePicker
import io.github.t1geryan.penny.ui.views.spacing.Expanded
import io.github.t1geryan.penny.ui.views.spacing.Spacer
import io.github.t1geryan.penny.ui.views.transactions.TransactionItem
import io.github.t1geryan.theme.icons
import io.github.t1geryan.theme.spacing
import kotlinx.datetime.LocalDateRange

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun TransactionsComponent(
    state: TransactionsState,
    onSendIntent: (TransactionsIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    ComponentWithTopBar(
        title = stringResource(R.string.screen_transactions_title),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier.fillMaxSize(),
        ) {
            Header(
                dateRangeFilter = state.dataRangeFilter,
                categoryFilter = state.categoryFilter,
                onSendIntent = onSendIntent,
                modifier = Modifier
                    .fillMaxWidth()
                    .background(MaterialTheme.colorScheme.surfaceVariant)
                    .padding(horizontal = MaterialTheme.spacing.normal),
            )
            HorizontalDivider(color = MaterialTheme.colorScheme.outlineVariant)
            Content(
                state = state,
                onSendIntent = onSendIntent,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }

    TransactionsDialog(dialogState = state.dialogState, onSendIntent = onSendIntent)

    val fabState = LocalFab.current
    val context = LocalContext.current
    val fabIcon = MaterialTheme.icons.add
    LifecycleResumeEffect(Unit) {
        fabState.setFab(
            icon = fabIcon,
            contentDescription = context.getString(R.string.common_cd_add_transaction),
            onClick = { onSendIntent(TransactionsIntent.AddTransaction) },
        )

        onPauseOrDispose {
            fabState.clearFab()
        }
    }
}

@Composable
private fun Content(
    state: TransactionsState,
    onSendIntent: (TransactionsIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    when (val emptyState = state.emptyState) {
        null -> TransactionsList(
            transactions = state.filteredTransactions,
            onSendIntent = onSendIntent,
            modifier = modifier,
        )

        else -> EmptyContent(
            emptyState = emptyState,
            onSendIntent = onSendIntent,
            modifier = modifier,
        )
    }
}

@Composable
fun EmptyContent(
    emptyState: TransactionsEmptyState,
    onSendIntent: (TransactionsIntent) -> Unit,
    modifier: Modifier,
) {
    val callback: () -> Unit = remember {
        when (emptyState) {
            TransactionsEmptyState.NO_TRANSACTIONS -> {
                { onSendIntent(TransactionsIntent.AddTransaction) }
            }

            TransactionsEmptyState.NO_TRANSACTIONS_THIS_PERIOD -> {
                { onSendIntent(TransactionsIntent.AddTransaction) }
            }

            TransactionsEmptyState.NO_TRANSACTIONS_THIS_FILTER -> {
                { onSendIntent(TransactionsIntent.ClearAllFilters) }
            }
        }
    }

    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Icon(
            emptyState.icon,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                .padding(MaterialTheme.spacing.normal),
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Spacer(MaterialTheme.spacing.normal)
        Text(emptyState.title, style = MaterialTheme.typography.titleLarge)
        Spacer(MaterialTheme.spacing.medium)
        Text(
            emptyState.description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(Percent.THREE_QUARTERS.fraction),
        )
        Spacer(MaterialTheme.spacing.large)
        when (emptyState.buttonStyle) {
            TransactionsEmptyState.ButtonStyle.Filled -> Button(onClick = callback) {
                Text(emptyState.buttonTitle)
            }

            TransactionsEmptyState.ButtonStyle.Outline -> OutlinedButton(
                onClick = callback,
                colors = ButtonDefaults.outlinedButtonColors(
                    contentColor = MaterialTheme.colorScheme.primary,
                ),
                border = BorderStroke(1.dp, MaterialTheme.colorScheme.primary),
            ) {
                Text(emptyState.buttonTitle)
            }
        }
    }
}

@Composable
private fun Header(
    dateRangeFilter: LocalDateRange?,
    categoryFilter: List<Category>,
    onSendIntent: (TransactionsIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    val isFilteredByData = dateRangeFilter != null
    val isFilteredByCategories = categoryFilter.isNotEmpty()

    Column(
        modifier = modifier,
    ) {
        Spacer(MaterialTheme.spacing.small)
        FiltrationRow(
            title = stringResource(R.string.screen_transaction_filter_by_category_title),
            icon = MaterialTheme.icons.filter,
            onClick = { onSendIntent(TransactionsIntent.PickFiltrationCategories) },
            modifier = Modifier.fillMaxWidth(),
            endContent = {
                val alpha by animateFloatAsState(if (isFilteredByCategories) 1.0f else 0.0f)
                Text(
                    categoryFilter.size.toString(),
                    style = MaterialTheme.typography.bodySmall,
                    color = MaterialTheme.colorScheme.onPrimary,
                    modifier = Modifier
                        .alpha(alpha)
                        .padding(end = MaterialTheme.spacing.small)
                        .background(
                            MaterialTheme.colorScheme.primary,
                            RoundedCornerShape(Percent.HALF.percents),
                        )
                        .padding(
                            horizontal = MaterialTheme.spacing.normal,
                            vertical = MaterialTheme.spacing.tiny,
                        ),
                )
            },
        )
        Spacer(MaterialTheme.spacing.small)
        FiltrationRow(
            title = dateRangeFilter?.format(LocalContext.current)
                ?: stringResource(R.string.screen_transaction_filter_by_date_title),
            icon = MaterialTheme.icons.calendar,
            titleStyle = MaterialTheme.typography.bodyMedium.copy(
                color = if (isFilteredByData) MaterialTheme.colorScheme.onSurface else MaterialTheme.colorScheme.primary,
                fontWeight = if (isFilteredByData) FontWeight.Normal else FontWeight.Medium,
            ),
            onClick = { onSendIntent(TransactionsIntent.PickFiltrationRange) },
            modifier = Modifier.fillMaxWidth(),
            endContent = {
                val alpha by animateFloatAsState(if (isFilteredByData) 1.0f else 0.0f)
                IconButton(
                    onClick = { onSendIntent(TransactionsIntent.SetFiltrationRange(null)) },
                    modifier = Modifier.alpha(alpha),
                ) {
                    Icon(
                        imageVector = MaterialTheme.icons.close,
                        contentDescription = stringResource(R.string.screen_transaction_filter_by_date_clear_cd),
                        tint = MaterialTheme.colorScheme.primary,
                    )
                }
            },
        )
    }
}

@Composable
private fun FiltrationRow(
    title: String,
    icon: ImageVector,
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    titleStyle: TextStyle = MaterialTheme.typography.bodyMedium.copy(
        color = MaterialTheme.colorScheme.primary,
        fontWeight = FontWeight.Medium,
    ),
    iconTint: Color = MaterialTheme.colorScheme.primary,
    contentAlignment: Alignment.Vertical = Alignment.CenterVertically,
    contentArrangement: Arrangement.Horizontal = Arrangement.Start,
    endContent: @Composable () -> Unit,
) {
    TextButton(
        onClick = onClick,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = contentAlignment,
            horizontalArrangement = contentArrangement,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(icon, contentDescription = null, tint = iconTint)
            Spacer(MaterialTheme.spacing.tiny)
            Text(title, style = titleStyle)
            Expanded()
            endContent()
        }
    }
}

@Composable
private fun TransactionsList(
    transactions: List<Transaction>,
    onSendIntent: (TransactionsIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    LazyColumn(
        contentPadding = PaddingValues(
            start = MaterialTheme.spacing.medium,
            end = MaterialTheme.spacing.medium,
            top = MaterialTheme.spacing.medium,
            bottom = MaterialTheme.spacing.giant,
        ),
        verticalArrangement = Arrangement.spacedBy(
            MaterialTheme.spacing.medium,
            Alignment.Top,
        ),
        modifier = modifier,
    ) {
        items(
            transactions,
            key = { transaction -> transaction.id },
        ) { transaction ->
            TransactionItem(
                transaction,
                onClicked = {
                    onSendIntent(TransactionsIntent.EditTransaction(transaction.id))
                },
                onDeleteClicked = {
                    onSendIntent(TransactionsIntent.DeleteTransaction(transaction.id))
                },
                modifier = Modifier
                    .fillMaxWidth(),
            )
        }
    }
}

@Composable
private fun TransactionsDialog(
    dialogState: TransactionsDialogState,
    onSendIntent: (TransactionsIntent) -> Unit,
) {
    when (dialogState) {
        TransactionsDialogState.DateRangeFilterPicker -> PennyDateRangePicker(
            onDismissRequest = { onSendIntent(TransactionsIntent.DismissDialog) },
            onRangeSelected = { onSendIntent(TransactionsIntent.SetFiltrationRange(it)) },
            modifier = Modifier.fillMaxHeight(fraction = 0.5f),
        )

        is TransactionsDialogState.CategoryFilterPicker -> BoxWithConstraints {
            CategoriesPicker(
                categories = dialogState.categories,
                initialSelectedCategories = dialogState.initialSelectedCategories,
                onDismissRequest = {
                    onSendIntent(TransactionsIntent.DismissDialog)
                },
                onCategoriesSelected = { categories ->
                    onSendIntent(TransactionsIntent.SetFiltrationCategories(categories))
                },
                modifier = Modifier
                    .heightIn(max = this.maxHeight * 0.75f)
                    .width(this.maxWidth - MaterialTheme.spacing.medium * 2),
            )
        }

        TransactionsDialogState.None -> {}
        is TransactionsDialogState.ConfirmTransactionDelete -> ConfirmationDialog(
            onDismissRequest = { onSendIntent(TransactionsIntent.DismissDialog) },
            onConfirm = { onSendIntent(TransactionsIntent.ConfirmTransactionDelete(dialogState.transactionId)) },
            title = stringResource(R.string.screen_transactions_delete_dialog_title),
            description = stringResource(R.string.screen_transactions_delete_dialog_text),
            confirmButtonTitle = stringResource(R.string.common_dialog_button_delete),
            confirmButtonColors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onErrorContainer,
                containerColor = MaterialTheme.colorScheme.errorContainer,
            ),
        )
    }
}
