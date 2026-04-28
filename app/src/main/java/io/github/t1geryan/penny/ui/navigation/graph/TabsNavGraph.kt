package io.github.t1geryan.penny.ui.navigation.graph

import androidx.compose.animation.fadeIn
import androidx.compose.animation.fadeOut
import androidx.compose.foundation.layout.Box
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
import io.github.t1geryan.navigation.TabsNavEntry
import io.github.t1geryan.penny.ui.features.categories.CategoriesComponent
import io.github.t1geryan.penny.ui.features.categories.CategoriesViewModel
import io.github.t1geryan.penny.ui.features.transactions.TransactionsComponent
import io.github.t1geryan.penny.ui.features.transactions.TransactionsViewModel
import io.github.t1geryan.penny.ui.navigation.actions.navigateFromCategoriesToCreateOrEditCategory
import io.github.t1geryan.penny.ui.navigation.actions.navigateFromTransactionsToCreateOrUpdateTransaction

@Composable
fun TabsNavGraph(
    modifier: Modifier = Modifier,
    rootNavController: NavController,
    tabsNavController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = tabsNavController,
        startDestination = TabsNavEntry.INITIAL,
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
        composeCategories(rootNavController)
        composeNotifications()
    }
}

private fun NavGraphBuilder.composeTransactions(rootNavController: NavController) {
    composable<TabsNavEntry.Transactions> {
        val viewModel = hiltViewModel<TransactionsViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        TransactionsComponent(
            state = state,
            onSendIntent = viewModel::receiveIntent,
            onNavigateToCreateOrUpdateTransaction = { transactionId ->
                rootNavController.navigateFromTransactionsToCreateOrUpdateTransaction(transactionId)
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}

private fun NavGraphBuilder.composeStatistics() {
    composable<TabsNavEntry.Statistics> {
        Box(modifier = Modifier.fillMaxSize())
    }
}

private fun NavGraphBuilder.composeCategories(rootNavController: NavController) {
    composable<TabsNavEntry.Categories> {
        val viewModel = hiltViewModel<CategoriesViewModel>()
        val state by viewModel.state.collectAsStateWithLifecycle()
        CategoriesComponent(
            state = state,
            onSendIntent = viewModel::receiveIntent,
            eventsFlow = viewModel.events,
            onNavigateToCreateOrEditCategory = { categoryId ->
                rootNavController.navigateFromCategoriesToCreateOrEditCategory(categoryId)
            },
            modifier = Modifier.fillMaxSize(),
        )
    }
}

private fun NavGraphBuilder.composeNotifications() {
    composable<TabsNavEntry.Notifications> {
        Box(modifier = Modifier.fillMaxSize())
    }
}
