package io.github.t1geryan.navigation

import kotlinx.serialization.Serializable

@Serializable
sealed interface RootNavEntry {

    @Serializable
    data object Tabs : RootNavEntry

    @Serializable
    data class CreateOrUpdateTransaction(val id: Int?) : RootNavEntry

    @Serializable
    data class CreateOrUpdateCategory(val id: Int?) : RootNavEntry

    companion object {
        val INITIAL = Tabs
    }
}