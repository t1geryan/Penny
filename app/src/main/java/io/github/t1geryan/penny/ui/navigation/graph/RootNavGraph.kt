package io.github.t1geryan.penny.ui.navigation.graph

import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import androidx.navigation.toRoute
import io.github.t1geryan.navigation.RootNavEntry
import io.github.t1geryan.penny.ui.features.category.CreateOrEditCategoryComponent
import io.github.t1geryan.penny.ui.features.category.CreateOrEditCategoryViewModel
import io.github.t1geryan.penny.ui.features.createtransaction.CreateOrUpdateTransactionComponent
import io.github.t1geryan.penny.ui.features.createtransaction.CreateOrUpdateTransactionViewModel
import io.github.t1geryan.penny.ui.features.tabs.TabsComponent

@Composable
fun RootNavGraph(
    modifier: Modifier = Modifier,
    rootNavController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = rootNavController,
        startDestination = RootNavEntry.INITIAL,
        modifier = modifier,
    ) {
        composeCreateOrUpdateCategory(rootNavController)
        composeCreateOrUpdateTransaction(rootNavController)
        composeTabs(rootNavController)
    }
}

private fun NavGraphBuilder.composeCreateOrUpdateCategory(navController: NavController) {
    composable<RootNavEntry.CreateOrUpdateCategory> {
        val route = it.toRoute<RootNavEntry.CreateOrUpdateCategory>()
        val viewModel = hiltViewModel<CreateOrEditCategoryViewModel, CreateOrEditCategoryViewModel.Factory> { factory ->
            factory.create(route.id)
        }
        val state by viewModel.state.collectAsStateWithLifecycle()
        CreateOrEditCategoryComponent(
            state = state,
            onSendIntent = viewModel::receiveIntent,
            eventsFlow = viewModel.events,
            onNavigateUp = navController::navigateUp,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

private fun NavGraphBuilder.composeCreateOrUpdateTransaction(navController: NavController) {
    composable<RootNavEntry.CreateOrUpdateTransaction> {
        val route = it.toRoute<RootNavEntry.CreateOrUpdateTransaction>()
        val viewModel =
            hiltViewModel<CreateOrUpdateTransactionViewModel, CreateOrUpdateTransactionViewModel.Factory> { factory ->
                factory.create(route.id)
            }
        val state by viewModel.state.collectAsStateWithLifecycle()
        CreateOrUpdateTransactionComponent(
            state = state,
            onSendIntent = viewModel::receiveIntent,
            onNavigateUp = navController::navigateUp,
            eventsFlow = viewModel.events,
            modifier = Modifier
                .fillMaxSize(),
        )
    }
}

private fun NavGraphBuilder.composeTabs(navController: NavController) {
    composable<RootNavEntry.Tabs> {
        TabsComponent(navController, modifier = Modifier.fillMaxSize())
    }
}
