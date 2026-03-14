package io.github.t1geryan.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface RootNavEntry {

    @Serializable
    data object Tabs

    @Serializable
    data class CreateOrUpdateTransaction(val id: Int?)

    @Serializable
    data class CreateOrUpdateCategory(val id: Int?)

    companion object {
        val INITIAL = Tabs
    }
}
