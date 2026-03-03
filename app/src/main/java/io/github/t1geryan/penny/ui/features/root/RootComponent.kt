package io.github.t1geryan.penny.ui.features.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.navigation3.runtime.rememberNavBackStack
import androidx.navigation3.ui.NavDisplay
import io.github.t1geryan.navigation.RootNavEntry
import io.github.t1geryan.penny.ui.navigation.graph.provideRootGraph

@Composable
fun RootComponent(modifier: Modifier = Modifier) {
    val rootBackStack = rememberNavBackStack(RootNavEntry.Tabs)

    NavDisplay(
        backStack = rootBackStack,
        modifier = modifier,
        onBack = { rootBackStack.removeLastOrNull() },
        entryProvider = { key ->
            provideRootGraph(rootBackStack, key as RootNavEntry)
        },
    )
}