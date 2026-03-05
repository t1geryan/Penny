package io.github.t1geryan.penny.ui.features.tabs

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.padding
import androidx.compose.material3.Icon
import androidx.compose.material3.NavigationBar
import androidx.compose.material3.NavigationBarItem
import androidx.compose.material3.Scaffold
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination
import androidx.navigation.compose.rememberNavController
import io.github.t1geryan.navigation.TabsNavEntry
import io.github.t1geryan.penny.ui.navigation.graph.TabsNavGraph
import kotlinx.coroutines.flow.collectLatest

@Composable
fun TabsComponent(
    rootNavController: NavController,
    modifier: Modifier = Modifier,
) {
    val tabsNavController = rememberNavController()
    var currentRoute by remember { mutableStateOf(TabsNavEntry.INITIAL.route) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                TabsNavEntry.ORDERED_TABS.forEach {
                    NavigationBarItem(
                        selected = it.route == currentRoute,
                        onClick = {
                            tabsNavController.navigate(it.route) {
                                popUpTo(tabsNavController.graph.findStartDestination().id) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
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
        TabsNavGraph(
            tabsNavController = tabsNavController,
            rootNavController = rootNavController,
            modifier = Modifier
                .fillMaxSize()
                .padding(paddingValues),
        )
    }

    LaunchedEffect(tabsNavController) {
        tabsNavController.currentBackStackEntryFlow.collectLatest {
            currentRoute = it.destination.route ?: TabsNavEntry.INITIAL.route
        }
    }
}

// TODO: style
@Composable
private fun TabsNavEntry.NavigationBarItemLabel(modifier: Modifier = Modifier) {
    Text(label)
}

@Composable
private fun TabsNavEntry.NavigationBarItemIcon(modifier: Modifier = Modifier) {
    Icon(icon, null)
}
