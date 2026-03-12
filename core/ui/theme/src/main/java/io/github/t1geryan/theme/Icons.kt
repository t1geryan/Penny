package io.github.t1geryan.theme

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.runtime.staticCompositionLocalOf
import androidx.compose.ui.graphics.vector.ImageVector
import io.github.t1geryan.icons.Add
import io.github.t1geryan.icons.ArrowBack
import io.github.t1geryan.icons.Calendar
import io.github.t1geryan.icons.Category
import io.github.t1geryan.icons.Close
import io.github.t1geryan.icons.Delete
import io.github.t1geryan.icons.Edit
import io.github.t1geryan.icons.Filter
import io.github.t1geryan.icons.Home
import io.github.t1geryan.icons.Notifications
import io.github.t1geryan.icons.Statistics

data class PennyIcons(
    val add: ImageVector = Add,
    val close: ImageVector = Close,
    val home: ImageVector = Home,
    val category: ImageVector = Category,
    val notifications: ImageVector = Notifications,
    val statistics: ImageVector = Statistics,
    val edit: ImageVector = Edit,
    val delete: ImageVector = Delete,
    val calendar: ImageVector = Calendar,
    val filter: ImageVector = Filter,
    val arrowBack: ImageVector = ArrowBack,
)

internal val LocalIcons = staticCompositionLocalOf { PennyIcons() }

val MaterialTheme.icons: PennyIcons
    @Composable
    @ReadOnlyComposable
    get() = LocalIcons.current