package io.github.t1geryan.penny.ui.views.icon

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

@Composable
fun TextIcon(
    text: String,
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
            text = text,
            fontSize = fontSize,
            lineHeight = fontSize,
        )
    }
}

@Composable
@Preview(showBackground = true)
fun TextIcon_Preview_Emoji() {
    TextIcon(
        text = "\uD83C\uDF54",
        backgroundColor = Color(0xFFE3A9A9),
    )
}

@Composable
@Preview(showBackground = true)
fun TextIcon_Preview_Currency() {
    TextIcon(
        text = "\u0024",
        backgroundColor = Color(0xFFE3A9A9),
    )
}
