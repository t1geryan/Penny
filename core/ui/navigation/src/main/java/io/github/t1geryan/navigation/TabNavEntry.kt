package io.github.t1geryan.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface TabNavEntry : NavKey {

    @Serializable
    data object Transactions : TabNavEntry

    @Serializable
    data object Statistics : TabNavEntry

    @Serializable
    data object Categories : TabNavEntry

    @Serializable
    data object Notifications : TabNavEntry

    companion object {
        val ORDERED_TABS = listOf(
            Transactions,
            Statistics,
            Categories,
            Notifications,
        )
    }
}