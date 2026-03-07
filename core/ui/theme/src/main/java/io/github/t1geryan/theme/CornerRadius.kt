package io.github.t1geryan.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.unit.Dp
import androidx.compose.ui.unit.dp

data class CornerRadius(
    val tiny: Dp = 2.dp,
    val small: Dp = 4.dp,
    val medium: Dp = 8.dp,
    val large: Dp = 12.dp,
    val extraLarge: Dp = 16.dp,
    val huge: Dp = 24.dp,
)

val LocalCornerRadius = staticCompositionLocalOf { CornerRadius() }

val MaterialTheme.cornerRadius
    @Composable
    @ReadOnlyComposable
    get() = LocalCornerRadius.current
