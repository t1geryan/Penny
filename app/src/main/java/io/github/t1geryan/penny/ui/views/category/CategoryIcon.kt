package io.github.t1geryan.penny.ui.views.category

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.penny.ui.contracts.backgroundColor
import io.github.t1geryan.penny.ui.views.icon.TextIcon

@Composable
fun CategoryIcon(
    category: Category,
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
) {
    TextIcon(
        text = category.emoji,
        backgroundColor = category.backgroundColor,
        modifier = modifier,
        size = size,
    )
}

@Composable
@Preview(showBackground = true)
fun CategoryIcon_Preview() {
    CategoryIcon(
        category = Category(
            id = 3,
            name = "Food & Dining",
            emoji = "\uD83C\uDF54",
            color = 0xFFDB1818,
            limit = null,
        ),
    )
}
