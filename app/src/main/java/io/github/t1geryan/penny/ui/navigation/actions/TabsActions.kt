package io.github.t1geryan.penny.ui.navigation.actions

import androidx.navigation.NavController
import androidx.navigation.NavGraph.Companion.findStartDestination

fun <T: Any> NavController.switchTab(tab: T) {
    navigate(route = tab) {
        popUpTo(graph.findStartDestination().id) {
            saveState = true
        }
        launchSingleTop = true
        restoreState = true
    }
}
