package io.github.t1geryan.penny.ui.views.picker

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.draw.clip
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.dp
import androidx.compose.ui.window.DialogProperties
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.models.Alpha
import io.github.t1geryan.penny.R
import io.github.t1geryan.penny.ui.views.category.CategoryIcon
import io.github.t1geryan.penny.ui.views.spacing.Expanded
import io.github.t1geryan.penny.ui.views.spacing.Spacer
import io.github.t1geryan.theme.cornerRadius
import io.github.t1geryan.theme.icons
import io.github.t1geryan.theme.spacing

@Composable
fun CategoriesPicker(
    categories: List<Category>,
    initialSelectedCategories: List<Category>,
    onDismissRequest: () -> Unit,
    onCategoriesSelected: (List<Category>) -> Unit,
    modifier: Modifier = Modifier,
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
) {
    ItemPicker(
        title = stringResource(R.string.screen_transaction_filter_by_category_title),
        items = categories,
        initiallySelectedItems = initialSelectedCategories,
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
                Expanded()
                val alpha by animateFloatAsState(if (isSelected) Alpha.OPAQUE.value else Alpha.TRANSPARENT.value)
                Icon(
                    MaterialTheme.icons.check,
                    contentDescription = null,
                    modifier = Modifier
                        .alpha(alpha)
                        .size(24.dp)
                        .background(MaterialTheme.colorScheme.primary, CircleShape)
                        .padding(MaterialTheme.spacing.extraSmall),
                    tint = MaterialTheme.colorScheme.onPrimary,
                )
            }
        },
        onDismissRequest = onDismissRequest,
        onItemsSelected = onCategoriesSelected,
        properties = properties,
        modifier = modifier,
    )
}

@Composable
@Preview(showBackground = true)
fun CategoriesPicker_Preview() {
    val categories = listOf(
        Category(
            id = 1,
            name = "Food",
            emoji = "🍔",
            color = 0xFFE57373,
            limit = null,
        ),
        Category(
            id = 2,
            name = "Transport",
            emoji = "🚌",
            color = 0xFF64B5F6,
            limit = null,
        ),
        Category(
            id = 3,
            name = "Entertainment",
            emoji = "🎮",
            color = 0xFFBA68C8,
            limit = null,
        ),
        Category(
            id = 4,
            name = "Healthcare",
            emoji = "🏥",
            color = 0xFFBAC4d4,
            limit = null,
        ),
        Category(
            id = 5,
            name = "Bills",
            emoji = "💡",
            color = 0xFFC4BA6A,
            limit = null,
        ),
        Category(
            id = 6,
            name = "Shopping",
            emoji = "🛍️",
            color = 0xFFCFA9CB,
            limit = null,
        ),
    )
    CategoriesPicker(
        categories = categories,
        initialSelectedCategories = categories.subList(0, 3),
        onDismissRequest = {},
        onCategoriesSelected = {},
    )
}
