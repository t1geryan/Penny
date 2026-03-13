package io.github.t1geryan.theme

import androidx.compose.foundation.isSystemInDarkTheme
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Typography
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider

@Composable
fun PennyTheme(
    icons: PennyIcons = MaterialTheme.icons,
    spacing: Spacing = MaterialTheme.spacing,
    darkTheme: Boolean = isSystemInDarkTheme(),
    typography: Typography = PennyTypography,
    cornerRadius: CornerRadius = CornerRadius(),
    content: @Composable () -> Unit,
) {
    val colorScheme = when {
        darkTheme -> darkScheme
        else -> lightScheme
    }

    CompositionLocalProvider(
        LocalSpacing provides spacing,
        LocalIcons provides icons,
        LocalCornerRadius provides cornerRadius,
    ) {
        MaterialTheme(
            colorScheme = colorScheme,
            typography = typography,
            content = content,
        )
    }
}
