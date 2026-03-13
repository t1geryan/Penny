package io.github.t1geryan.penny.ui.navigation.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.t1geryan.navigation.RootNavEntry
import io.github.t1geryan.penny.ui.features.tabs.TabsComponent

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    rootNavController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = rootNavController,
        startDestination = RootNavEntry.INITIAL.route,
        modifier = modifier,
    ) {
        composeCreateOrUpdateCategory()
        composeCreateOrUpdateTransaction()
        composeTabs(rootNavController)
    }
}

private fun NavGraphBuilder.composeCreateOrUpdateCategory() {
    composable(
        route = RootNavEntry.CREATE_OR_UPDATE_CATEGORY.route,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Cyan),
        )
    }
}

private fun NavGraphBuilder.composeCreateOrUpdateTransaction() {
    composable(
        route = RootNavEntry.CREATE_OR_UPDATE_TRANSACTION.route,
    ) {
        Box(
            modifier = Modifier
                .fillMaxSize()
                .background(Color.Magenta),
        )
    }
}

private fun NavGraphBuilder.composeTabs(navController: NavController) {
    composable(
        route = RootNavEntry.TABS.route,
    ) {
        TabsComponent(navController, modifier = Modifier.fillMaxSize())
    }
}
