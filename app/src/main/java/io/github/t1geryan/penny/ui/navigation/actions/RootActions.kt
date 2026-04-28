package io.github.t1geryan.penny.ui.navigation.actions

import androidx.navigation.NavController
import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.navigation.RootNavEntry

internal fun NavController.navigateFromTransactionsToCreateOrUpdateTransaction(transactionId: TransactionId?) {
    navigate(route = RootNavEntry.CreateOrUpdateTransaction(transactionId)) {
        popUpTo(route = RootNavEntry.Tabs)
        launchSingleTop = true
    }
}

internal fun NavController.navigateFromCategoriesToCreateOrEditCategory(categoryId: CategoryId?) {
    navigate(route = RootNavEntry.CreateOrUpdateCategory(categoryId)) {
        popUpTo(route = RootNavEntry.Tabs)
        launchSingleTop = true
    }
}
