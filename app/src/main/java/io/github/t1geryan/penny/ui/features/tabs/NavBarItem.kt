package io.github.t1geryan.penny.ui.features.tabs

import androidx.compose.material3.MaterialTheme
import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import io.github.t1geryan.navigation.TabNavEntry
import io.github.t1geryan.penny.R
import io.github.t1geryan.theme.icons

val TabNavEntry.label
    @Composable
    @ReadOnlyComposable
    get() = stringResource(
        when (this) {
            TabNavEntry.Categories -> R.string.screen_tab_item_categories_label
            TabNavEntry.Notifications -> R.string.screen_tab_item_notifications_label
            TabNavEntry.Statistics -> R.string.screen_tab_item_statistics_label
            TabNavEntry.Transactions -> R.string.screen_tab_item_transactions_label
        }
    )

val TabNavEntry.icon
    @Composable
    @ReadOnlyComposable
    get() = when (this) {
        TabNavEntry.Categories -> MaterialTheme.icons.category
        TabNavEntry.Notifications -> MaterialTheme.icons.notifications
        TabNavEntry.Statistics -> MaterialTheme.icons.statistics
        TabNavEntry.Transactions -> MaterialTheme.icons.home
    }