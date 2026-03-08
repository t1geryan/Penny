package io.github.t1geryan.penny.ui.views.transactions

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.penny.ui.contracts.format
import io.github.t1geryan.penny.ui.contracts.formatDate
import io.github.t1geryan.penny.ui.views.category.CategoryTag
import io.github.t1geryan.penny.ui.views.spacing.Expanded
import io.github.t1geryan.penny.ui.views.spacing.Spacer
import io.github.t1geryan.theme.cornerRadius
import io.github.t1geryan.theme.icons
import io.github.t1geryan.theme.spacing
import kotlinx.datetime.LocalDateTime

@Composable
fun TransactionItem(
    transaction: Transaction,
    modifier: Modifier = Modifier,
    onEditClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
) {
    TransactionItem(
        name = transaction.name,
        formattedAmount = transaction.amount.format(),
        formattedDate = transaction.formatDate(LocalContext.current),
        category = transaction.category,
        modifier = modifier,
        onEditClicked = onEditClicked,
        onDeleteClicked = onDeleteClicked,
    )
}

@Composable
fun TransactionItem(
    name: String,
    formattedAmount: String,
    formattedDate: String,
    category: Category,
    modifier: Modifier = Modifier,
    onEditClicked: () -> Unit,
    onDeleteClicked: () -> Unit,
) {
    Column(
        modifier = modifier
            .clip(RoundedCornerShape(MaterialTheme.cornerRadius.large))
            .border(
                1.dp,
                MaterialTheme.colorScheme.onSurface,
                RoundedCornerShape(MaterialTheme.cornerRadius.large),
            )
            .background(MaterialTheme.colorScheme.surfaceContainer)
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.normal,
            ),
    ) {
        Row(modifier = Modifier.fillMaxWidth()) {
            Text(name, style = MaterialTheme.typography.titleMedium)
            Expanded()
            Text(
                formattedAmount,
                color = MaterialTheme.colorScheme.primary,
                style = MaterialTheme.typography.titleLarge,
            )
        }
        Spacer(MaterialTheme.spacing.tiny)
        Text(formattedDate, style = MaterialTheme.typography.bodySmall)
        Row(
            modifier = Modifier.fillMaxWidth(),
            verticalAlignment = Alignment.CenterVertically,
        ) {
            CategoryTag(category)
            Expanded()
            IconButton(onClick = onEditClicked) {
                Icon(
                    MaterialTheme.icons.edit,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.onSurface,
                )
            }
            IconButton(onClick = onDeleteClicked) {
                Icon(
                    MaterialTheme.icons.delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        }
    }
}

@Composable
@Preview(showBackground = true)
fun TransactionItem_Preview() {
    TransactionItem(
        transaction = Transaction(
            id = 1,
            name = "Burger",
            amount = Amount(4599, Currency.US_DOLLAR),
            category = Category(
                id = 3,
                name = "Food & Dining",
                emoji = "\uD83C\uDF54",
                color = 0xFFDB1818,
                limit = null,
            ),
            date = LocalDateTime(2026, 3, 1, 13, 20),
        ),
    )
}