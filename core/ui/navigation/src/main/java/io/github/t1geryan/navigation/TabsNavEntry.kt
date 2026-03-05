package io.github.t1geryan.navigation

enum class TabsNavEntry(val route: String) {
    TRANSACTIONS("tabs_transactions"),
    STATISTICS("tabs_statistics"),
    CATEGORIES("tabs_categories"),
    NOTIFICATIONS("tabs_notifications"),
    ;

    companion object {
        val ORDERED_TABS = listOf(
            TRANSACTIONS,
            STATISTICS,
            CATEGORIES,
            NOTIFICATIONS,
        )

        val INITIAL: TabsNavEntry = TRANSACTIONS
    }
}