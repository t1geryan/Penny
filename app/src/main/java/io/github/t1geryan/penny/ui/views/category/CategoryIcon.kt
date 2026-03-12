package io.github.t1geryan.penny.ui.views.category

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.platform.LocalDensity
import androidx.compose.ui.tooling.preview.Preview
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.penny.ui.contracts.backgroundColor

@Composable
fun CategoryIcon(
    category: Category,
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
) {
    CategoryIcon(
        emoji = category.emoji,
        backgroundColor = category.backgroundColor,
        modifier = modifier,
        size = size,
    )
}

@Composable
fun CategoryIcon(
    emoji: String,
    backgroundColor: Color,
    modifier: Modifier = Modifier,
    size: Dp = 36.dp,
) {
    BoxWithConstraints(
        contentAlignment = Alignment.Center,
        modifier = modifier
            .size(size)
            .clip(CircleShape)
            .background(backgroundColor),
    ) {

        val density = LocalDensity.current
        val fontScale = density.fontScale

        val fontSize = with(density) {
            ((maxWidth / 2) / fontScale).toSp()
        }

        Text(
            text = emoji,
            fontSize = fontSize,
            lineHeight = fontSize,
        )
    }
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
