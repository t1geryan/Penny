package io.github.t1geryan.penny.ui.views.category

import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.LinearProgressIndicator
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.tooling.preview.PreviewFontScale
import androidx.compose.ui.tooling.preview.PreviewParameter
import androidx.compose.ui.tooling.preview.PreviewParameterProvider
import androidx.compose.ui.unit.dp
import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.penny.ui.contracts.backgroundColor
import io.github.t1geryan.penny.ui.contracts.contentColor
import io.github.t1geryan.penny.ui.contracts.format
import io.github.t1geryan.penny.ui.views.icon.TextIcon
import io.github.t1geryan.penny.ui.views.spacing.Expanded
import io.github.t1geryan.penny.ui.views.spacing.Spacer
import io.github.t1geryan.theme.cornerRadius
import io.github.t1geryan.theme.icons
import io.github.t1geryan.theme.spacing

@Composable
fun CategoryItem(
    category: Category,
    spentAmount: Amount,
    modifier: Modifier = Modifier,
    onClicked: () -> Unit = {},
    onDeleteClicked: () -> Unit = {},
) {
    CategoryItem(
        categoryName = category.name,
        categoryEmoji = category.emoji,
        categoryColor = category.contentColor,
        categoryBackgroundColor = category.backgroundColor,
        spentAmount = spentAmount,
        categoryLimit = category.limit,
        onClicked = onClicked,
        onDeleteClicked = onDeleteClicked,
        modifier = modifier,
    )
}

@Composable
fun CategoryItem(
    spentAmount: Amount,
    categoryLimit: Amount?,
    categoryName: String,
    categoryEmoji: String,
    categoryColor: Color,
    categoryBackgroundColor: Color,
    modifier: Modifier = Modifier,
    onClicked: () -> Unit,
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
            .clickable {
                onClicked()
            }
            .padding(
                horizontal = MaterialTheme.spacing.medium,
                vertical = MaterialTheme.spacing.normal,
            ),
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier.fillMaxWidth(),
        ) {
            TextIcon(
                text = categoryEmoji,
                backgroundColor = categoryBackgroundColor,
            )
            Spacer(MaterialTheme.spacing.medium)
            Text(categoryName, style = MaterialTheme.typography.titleMedium)
            Expanded()
            IconButton(onClick = onDeleteClicked) {
                Icon(
                    MaterialTheme.icons.delete,
                    contentDescription = null,
                    tint = MaterialTheme.colorScheme.error,
                )
            }
        }
        if (categoryLimit != null) {
            Spacer(MaterialTheme.spacing.normal)
            LinearProgressIndicator(
                progress = { spentAmount.calculateSpentToLimitRatio(categoryLimit) },
                color = categoryColor,
                trackColor = MaterialTheme.colorScheme.outlineVariant,
                gapSize = (-MaterialTheme.spacing.small),
                drawStopIndicator = {},
                modifier = Modifier
                    .fillMaxWidth()
                    .height(10.dp),
            )
            Spacer(MaterialTheme.spacing.extraSmall)
            Row(modifier = Modifier.fillMaxWidth()) {
                Text(
                    text = spentAmount.format(),
                )
                Expanded()
                Text(
                    text = categoryLimit.format(),
                )
            }
            Spacer(MaterialTheme.spacing.medium)
            Text(
                text = spentAmount.calculateRemaining(categoryLimit).format(),
            )
        }
    }
}

@Composable
@Preview(showBackground = true)
@PreviewFontScale
fun CategoryItem_Preview(
    @PreviewParameter(CategoryAndSpentAmountProvider::class) categoryAndSpent: Pair<Category, Amount>,
) {
    CategoryItem(
        category = categoryAndSpent.first,
        spentAmount = categoryAndSpent.second,
    )
}

@Suppress("MagicNumber")
private class CategoryAndSpentAmountProvider : PreviewParameterProvider<Pair<Category, Amount>> {

    private val name = "Food & Dining"
    private val emoji = "\uD83C\uDF54"

    override val values: Sequence<Pair<Category, Amount>> = sequenceOf(
        Category(
            id = 3,
            name = name,
            emoji = emoji,
            color = 0xFFDB1818,
            limit = Amount(100f, Currency.US_DOLLAR),
            currency = Currency.US_DOLLAR,
        ) to Amount(100f, Currency.US_DOLLAR),
        Category(
            id = 3,
            name = name,
            emoji = emoji,
            color = 0xFFDB1818,
            limit = Amount(100f, Currency.US_DOLLAR),
            currency = Currency.US_DOLLAR,
        ) to Amount(50f, Currency.US_DOLLAR),
        Category(
            id = 3,
            name = name,
            emoji = emoji,
            color = 0xFFDB1818,
            limit = Amount(99.9f, Currency.US_DOLLAR),
            currency = Currency.US_DOLLAR,
        ) to Amount(33.3f, Currency.US_DOLLAR),
        Category(
            id = 3,
            name = name,
            emoji = emoji,
            color = 0xFFDB1818,
            limit = null,
            currency = Currency.US_DOLLAR,
        ) to Amount(33.3f, Currency.US_DOLLAR),
    )
}
