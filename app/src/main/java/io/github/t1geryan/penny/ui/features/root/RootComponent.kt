package io.github.t1geryan.penny.ui.features.root

import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import io.github.t1geryan.penny.ui.navigation.graph.RootNavGraph

@Composable
fun RootComponent(modifier: Modifier = Modifier) {
    RootNavGraph(modifier = modifier)
}
