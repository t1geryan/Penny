package io.github.t1geryan.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.t1geryan.icons.Add

data class PennyIcons(
    val add: ImageVector = Add,
)

internal val LocalIcons = staticCompositionLocalOf { PennyIcons() }

val MaterialTheme.icons: PennyIcons
    @Composable
    @ReadOnlyComposable
    get() = LocalIcons.current