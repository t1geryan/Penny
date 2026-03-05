package io.github.t1geryan.penny.ui.navigation.graph

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.t1geryan.navigation.TabsNavEntry

@Composable
fun TabsNavGraph(
    modifier: Modifier = Modifier,
    rootNavController: NavController,
    tabsNavController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = tabsNavController,
        startDestination = TabsNavEntry.INITIAL.route,
        modifier = modifier,
    ) {
        composeCategories()
        composeTransaction(rootNavController)
        composeNotifications()
        composeStatistics()
    }
}

private fun NavGraphBuilder.composeCategories() {
    composable(
        route = TabsNavEntry.CATEGORIES.route,
    ) {
    }
}

private fun NavGraphBuilder.composeTransaction(rootNavController: NavController) {
    composable(
        route = TabsNavEntry.TRANSACTIONS.route,
    ) {
    }
}

private fun NavGraphBuilder.composeNotifications() {
    composable(
        route = TabsNavEntry.NOTIFICATIONS.route,
    ) {
    }
}

private fun NavGraphBuilder.composeStatistics() {
    composable(
        route = TabsNavEntry.STATISTICS.route,
    ) {
    }
}
