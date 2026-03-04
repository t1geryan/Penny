package io.github.t1geryan.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface TabsNavEntry {

    @Serializable
    data object Transactions : TabsNavEntry

    @Serializable
    data object Statistics : TabsNavEntry

    @Serializable
    data object Categories : TabsNavEntry

    @Serializable
    data object Notifications : TabsNavEntry

    companion object {
        val ORDERED_TABS = listOf(
            Transactions,
            Statistics,
            Categories,
            Notifications,
        )

        val INITIAL: TabsNavEntry = Transactions
    }
}