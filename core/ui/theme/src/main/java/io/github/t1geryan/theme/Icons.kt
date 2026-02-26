package io.github.t1geryan.theme

import androidx.compose.material.icons.Icons
import androidx.compose.material.icons.outlined.Add
import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector

data class PennyIcons(
    val add: ImageVector = Icons.Outlined.Add,
)

internal val LocalIcons = staticCompositionLocalOf { PennyIcons() }

val MaterialTheme.icons: PennyIcons
    @Composable
    @ReadOnlyComposable
    get() = LocalIcons.current