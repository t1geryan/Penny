package io.github.t1geryan.penny.ui.navigation.graph

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
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
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.navigation.TabsNavEntry
import io.github.t1geryan.penny.ui.features.transactions.TransactionsComponent
import io.github.t1geryan.penny.ui.features.transactions.TransactionsNavDelegate
import io.github.t1geryan.penny.ui.features.transactions.TransactionsViewModel
import io.github.t1geryan.penny.ui.navigation.actions.navigateFromTransactionsToCreateOrUpdateTransaction

@Composable
fun TabsNavGraph(
    modifier: Modifier = Modifier,
    rootNavController: NavController,
    tabsNavController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = tabsNavController,
        startDestination = TabsNavEntry.INITIAL.route,
        enterTransition = {
            fadeIn()
        },
        exitTransition = {
            fadeOut()
        },
        modifier = modifier,
    ) {
        composeTransactions(rootNavController)
        composeStatistics()
        composeCategories()
        composeNotifications()
    }
}

private fun NavGraphBuilder.composeTransactions(rootNavController: NavController) {
    composable(
        route = TabsNavEntry.TRANSACTIONS.route,
    ) {
        val navDelegate = object : TransactionsNavDelegate {
            override fun navigateToCreateOrEdit(transactionId: TransactionId?) {
                rootNavController.navigateFromTransactionsToCreateOrUpdateTransaction(transactionId)
            }
        }
        val viewModel = hiltViewModel<TransactionsViewModel, TransactionsViewModel.Factory> { factory ->
            factory.provide(navDelegate)
        }
        val state by viewModel.state.collectAsStateWithLifecycle()
        TransactionsComponent(
            state = state,
            onSendIntent = viewModel::receiveIntent,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

private fun NavGraphBuilder.composeStatistics() {
    composable(
        route = TabsNavEntry.STATISTICS.route,
    ) {
    }
}

private fun NavGraphBuilder.composeCategories() {
    composable(
        route = TabsNavEntry.CATEGORIES.route,
    ) {

    }
}

private fun NavGraphBuilder.composeNotifications() {
    composable(
        route = TabsNavEntry.NOTIFICATIONS.route,
    ) {
    }
}
