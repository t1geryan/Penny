package io.github.t1geryan.penny.ui.views.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.tooling.preview.Preview
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.penny.ui.contracts.backgroundColor
import io.github.t1geryan.penny.ui.contracts.contentColor
import io.github.t1geryan.theme.spacing

@Composable
fun CategoryTag(
    category: Category,
    modifier: Modifier = Modifier,
) {
    CategoryTag(
        categoryName = category.name,
        categoryEmoji = category.emoji,
        categoryColor = category.contentColor,
        categoryBackgroundColor = category.backgroundColor,
        modifier = modifier,
    )
}

@Composable
fun CategoryTag(
    categoryName: String,
    categoryEmoji: String,
    categoryColor: Color,
    categoryBackgroundColor: Color,
    modifier: Modifier = Modifier,
) {
    Box(
        modifier = modifier
            .clip(RoundedCornerShape(percent = 50))
            .background(categoryBackgroundColor)
            .padding(
                horizontal = MaterialTheme.spacing.normal,
                vertical = MaterialTheme.spacing.extraSmall,
            ),
    ) {
        Row(verticalAlignment = Alignment.CenterVertically) {
            Text(categoryEmoji, fontSize = MaterialTheme.typography.labelSmall.fontSize)
            Text(categoryName, color = categoryColor, style = MaterialTheme.typography.labelSmall)
        }
    }
}

@Composable
@Preview(showBackground = true)
fun CategoryTag_Preview() {
    CategoryTag(
        category = Category(
            id = 3,
            name = "Food & Dining",
            emoji = "\uD83C\uDF54",
            color = 0xFFDB1818,
            limit = null,
        ),
    )
}
