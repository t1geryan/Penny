package io.github.t1geryan.penny.ui.navigation.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.hilt.lifecycle.viewmodel.compose.hiltViewModel
import androidx.lifecycle.compose.collectAsStateWithLifecycle
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.t1geryan.navigation.NavArg
import io.github.t1geryan.navigation.RootNavEntry
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
        route = "${RootNavEntry.CREATE_OR_UPDATE_TRANSACTION.route}/{${NavArg.TRANSACTION_ID_ARG.pathName}}",
    ) {
        val transactionId =
            it.arguments?.getString(NavArg.TRANSACTION_ID_ARG.pathName)?.toIntOrNull()
        val viewModel =
            hiltViewModel<CreateOrUpdateTransactionViewModel, CreateOrUpdateTransactionViewModel.Factory> { factory ->
                factory.create(transactionId)
            }
        val state by viewModel.state.collectAsStateWithLifecycle()
        CreateOrUpdateTransactionComponent(
            state = state,
            onSendIntent = viewModel::receiveIntent,
            modifier = Modifier
                .fillMaxSize(),
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
