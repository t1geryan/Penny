package io.github.t1geryan.penny.ui.features.tabs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.lifecycle.viewmodel.navigation3.rememberViewModelStoreNavEntryDecorator
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavKey
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.runtime.rememberSaveableStateHolderNavEntryDecorator
import androidx.navigation3.ui.NavDisplay
import io.github.t1geryan.navigation.TabNavEntry
import io.github.t1geryan.penny.ui.navigation.ext.bringToFront
import io.github.t1geryan.penny.ui.navigation.graph.provideTabsGraph

@Composable
fun TabsComponent(
    rootBackStack: NavBackStack<NavKey>,
    modifier: Modifier = Modifier,
) {
    val tabsBackStack = rememberNavBackStack(TabNavEntry.Transactions)

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                TabNavEntry.ORDERED_TABS.forEach {
                    NavigationBarItem(
                        selected = tabsBackStack.last() == it,
                        onClick = {
                            tabsBackStack.bringToFront(it)
                        },
                        icon = {
                            it.NavigationBarItemIcon()
                        },
                        label = {
                            it.NavigationBarItemLabel()
                        },
                    )
                }
            }
        },
    ) { paddingValues ->
        NavDisplay(
            backStack = tabsBackStack,
            entryDecorators = listOf(
                rememberSaveableStateHolderNavEntryDecorator(),
                rememberViewModelStoreNavEntryDecorator(),
            ),
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
            onBack = {
                if (tabsBackStack.size == 1) {
                    rootBackStack.removeLastOrNull()
                } else {
                    tabsBackStack.removeAll { it != TabNavEntry.Transactions }
                }
            },
            entryProvider = { key ->
                provideTabsGraph(rootBackStack, tabsBackStack, key as TabNavEntry)
            },
        )
    }
}

// TODO: style
@Composable
private fun TabNavEntry.NavigationBarItemLabel(modifier: Modifier = Modifier) {
    Text(label)
}

@Composable
private fun TabNavEntry.NavigationBarItemIcon(modifier: Modifier = Modifier) {
    Icon(icon, null)
}
