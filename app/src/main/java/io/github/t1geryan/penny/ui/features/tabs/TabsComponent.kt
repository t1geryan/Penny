package io.github.t1geryan.penny.ui.features.tabs

import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.WindowInsetsSides
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.only
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.systemBars
import androidx.compose.material3.FloatingActionButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.NavigationBarItemDefaults
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.CompositionLocalProvider
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavDestination.Companion.hasRoute
import androidx.navigation.compose.currentBackStackEntryAsState
import androidx.navigation.compose.rememberNavController
import io.github.t1geryan.navigation.TabsNavEntry
import io.github.t1geryan.penny.ui.navigation.actions.switchTab
import io.github.t1geryan.penny.ui.navigation.graph.TabsNavGraph
import io.github.t1geryan.penny.ui.utils.LocalFab
import io.github.t1geryan.penny.ui.utils.rememberFabState

@Composable
fun TabsComponent(
    rootNavController: NavController,
    modifier: Modifier = Modifier,
) {
    val tabsNavController = rememberNavController()
    val currentRoute by tabsNavController.currentBackStackEntryAsState()

    val fabState = rememberFabState()

    CompositionLocalProvider(LocalFab provides fabState) {
        Scaffold(
            contentWindowInsets = WindowInsets.systemBars.only(
                WindowInsetsSides.Horizontal,
            ),
            modifier = modifier,
            bottomBar = {
                NavigationBar(
                    containerColor = MaterialTheme.colorScheme.surfaceVariant,
                ) {
                    TabsNavEntry.ORDERED_TABS.forEach {
                        NavigationBarItem(
                            selected = currentRoute?.destination?.hasRoute(it::class) ?: false,
                            onClick = {
                                tabsNavController.switchTab(it)
                            },
                            icon = {
                                it.NavigationBarItemIcon()
                            },
                            label = {
                                it.NavigationBarItemLabel()
                            },
                            colors = NavigationBarItemDefaults.colors(
                                indicatorColor = Color.Transparent,
                                selectedIconColor = MaterialTheme.colorScheme.primary,
                                selectedTextColor = MaterialTheme.colorScheme.primary,
                                unselectedIconColor = MaterialTheme.colorScheme.onSurfaceVariant,
                                unselectedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                            ),
                        )
                    }
                }
            },
            floatingActionButton = {
                fabState.icon?.let { icon ->
                    FloatingActionButton(
                        onClick = fabState.onClick,
                    ) {
                        Icon(icon, contentDescription = fabState.contentDescription)
                    }
                }
            },
            floatingActionButtonPosition = fabState.position,
        ) { paddingValues ->
            TabsNavGraph(
                tabsNavController = tabsNavController,
                rootNavController = rootNavController,
                modifier = Modifier
                    .fillMaxSize()
                    .padding(paddingValues),
            )
        }
    }
}

@Composable
private fun TabsNavEntry.NavigationBarItemLabel(modifier: Modifier = Modifier) {
    Text(
        text = label,
        modifier = modifier,
    )
}

@Composable
private fun TabsNavEntry.NavigationBarItemIcon(modifier: Modifier = Modifier) {
    Icon(
        imageVector = icon,
        contentDescription = null,
        modifier = modifier,
    )
}
