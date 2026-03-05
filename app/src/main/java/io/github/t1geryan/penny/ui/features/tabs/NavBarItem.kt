package io.github.t1geryan.penny.ui.features.tabs

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import io.github.t1geryan.navigation.TabsNavEntry
import io.github.t1geryan.penny.R
import io.github.t1geryan.theme.icons

val TabsNavEntry.label
    @Composable
    @ReadOnlyComposable
    get() = stringResource(
        when (this) {
            TabsNavEntry.CATEGORIES -> R.string.screen_tab_item_categories_label
            TabsNavEntry.NOTIFICATIONS -> R.string.screen_tab_item_notifications_label
            TabsNavEntry.STATISTICS -> R.string.screen_tab_item_statistics_label
            TabsNavEntry.TRANSACTIONS -> R.string.screen_tab_item_transactions_label
        }
    )

val TabsNavEntry.icon
    @Composable
    @ReadOnlyComposable
    get() = when (this) {
        TabsNavEntry.CATEGORIES -> MaterialTheme.icons.category
        TabsNavEntry.NOTIFICATIONS -> MaterialTheme.icons.notifications
        TabsNavEntry.STATISTICS -> MaterialTheme.icons.statistics
        TabsNavEntry.TRANSACTIONS -> MaterialTheme.icons.home
    }