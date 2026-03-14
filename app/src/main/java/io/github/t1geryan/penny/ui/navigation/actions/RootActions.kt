package io.github.t1geryan.penny.ui.navigation.actions

import androidx.navigation.NavController
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.navigation.RootNavEntry

internal fun NavController.navigateFromTransactionsToCreateOrUpdateTransaction(transactionId: TransactionId?) {
    navigate(route = "${RootNavEntry.CREATE_OR_UPDATE_TRANSACTION.route}/$transactionId") {
        popUpTo(route = RootNavEntry.TABS.route)
        launchSingleTop = true
    }
}
