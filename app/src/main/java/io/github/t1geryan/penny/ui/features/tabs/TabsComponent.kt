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
    var currentState by remember { mutableStateOf(TabsNavEntry.INITIAL) }

    Scaffold(
        modifier = modifier,
        bottomBar = {
            NavigationBar {
                TabsNavEntry.ORDERED_TABS.forEach {
                    NavigationBarItem(
                        selected = it == currentState,
                        onClick = {
                            tabsNavController.navigate(it) {
                                popUpTo(TabsNavEntry.INITIAL) {
                                    saveState = true
                                }
                                launchSingleTop = true
                                restoreState = true
                            }
                            currentState = it
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
            // FIXME: currentState = it.destination.
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
