package io.github.t1geryan.navigation

import androidx.navigation3.runtime.NavKey
import kotlinx.serialization.Serializable

@Serializable
sealed interface RootNavEntry : NavKey {

    @Serializable
    data object Tabs : RootNavEntry

    @Serializable
    data class CreateOrUpdateTransaction(val id: Int?) : RootNavEntry

    @Serializable
    data class CreateOrUpdateCategory(val id: Int?) : RootNavEntry
}