package io.github.t1geryan.penny.ui.features.transactions

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
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
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.TextStyle
import androidx.compose.ui.text.font.FontWeight
import androidx.compose.ui.unit.dp
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.penny.R
import io.github.t1geryan.penny.ui.contracts.format
import io.github.t1geryan.penny.ui.views.category.CategoryIcon
import io.github.t1geryan.penny.ui.views.core.ComponentWithTopBar
import io.github.t1geryan.penny.ui.views.picker.ItemPicker
import io.github.t1geryan.penny.ui.views.picker.PennyDateRangePicker
import io.github.t1geryan.penny.ui.views.spacing.Expanded
import io.github.t1geryan.penny.ui.views.spacing.Spacer
import io.github.t1geryan.penny.ui.views.transactions.TransactionItem
import io.github.t1geryan.theme.cornerRadius
import io.github.t1geryan.theme.icons
import io.github.t1geryan.theme.spacing
import kotlinx.datetime.LocalDateRange

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
            TransactionsList(
                transactions = state.filteredTransactions,
                onSendIntent = onSendIntent,
                modifier = Modifier.fillMaxSize(),
            )
        }
    }

    TransactionsDialog(dialogState = state.dialogState, onSendIntent = onSendIntent)
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
                        .background(MaterialTheme.colorScheme.primary, RoundedCornerShape(50))
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
            horizontal = MaterialTheme.spacing.medium,
            vertical = MaterialTheme.spacing.medium,
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
                onEditClicked = { },
                onDeleteClicked = { },
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
            ItemPicker(
                title = stringResource(R.string.screen_transaction_filter_by_category_title),
                items = dialogState.categories,
                initiallySelectedItems = dialogState.initialSelectedCategories,
                enableWhenNoItemsSelected = true,
                itemContent = { item, isSelected, onSelect ->
                    Row(
                        modifier = Modifier
                            .fillMaxWidth()
                            .clip(RoundedCornerShape(MaterialTheme.cornerRadius.large))
                            .border(
                                1.dp,
                                color = if (isSelected) MaterialTheme.colorScheme.primary else MaterialTheme.colorScheme.outlineVariant,
                                shape = RoundedCornerShape(MaterialTheme.cornerRadius.large),
                            )
                            .clickable(
                                onClick = {
                                    onSelect()
                                },
                            )
                            .padding(MaterialTheme.spacing.normal),
                        verticalAlignment = Alignment.CenterVertically,
                    ) {
                        CategoryIcon(item)
                        Spacer(MaterialTheme.spacing.normal)
                        Text(item.name, style = MaterialTheme.typography.titleMedium)
                    }
                },
                onDismissRequest = { onSendIntent(TransactionsIntent.DismissDialog) },
                onItemsSelected = { items ->
                    onSendIntent(TransactionsIntent.SetFiltrationCategories(items))
                },
                modifier = Modifier
                    .heightIn(max = this.maxHeight * 0.75f)
                    .width(this.maxWidth - MaterialTheme.spacing.medium * 2),
            )
        }

        TransactionsDialogState.None -> {}
    }
}
