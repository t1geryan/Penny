package io.github.t1geryan.navigation

enum class RootNavEntry(val route: String) {
    TABS("root_tabs"),
    CREATE_OR_UPDATE_TRANSACTION("root_create_or_update_transaction"),
    CREATE_OR_UPDATE_CATEGORY("root_create_or_update_category"),
    ;

    companion object {
        val INITIAL = TABS
    }
}