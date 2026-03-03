package io.github.t1geryan.penny.ui.navigation.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import io.github.t1geryan.navigation.RootNavEntry
import io.github.t1geryan.navigation.TabNavEntry

fun provideTabsGraph(
    rootBackStack: NavBackStack<NavKey>,
    backStack: NavBackStack<NavKey>,
    key: TabNavEntry,
): NavEntry<NavKey> = when (key) {
    TabNavEntry.Categories -> NavEntry(key) {
        Stub(rootBackStack)
    }

    TabNavEntry.Notifications -> NavEntry(key) {
        Stub(rootBackStack)
    }

    TabNavEntry.Statistics -> NavEntry(key) {
        Stub(rootBackStack)
    }

    TabNavEntry.Transactions -> NavEntry(key) {
        Stub(rootBackStack)
    }
}

@Composable
private fun Stub(rootBackStack: NavBackStack<NavKey>) {
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green),
    ) {
        Text("AAA", modifier = Modifier.align(Alignment.TopCenter))
        Button(
            onClick = {
                rootBackStack.add(RootNavEntry.CreateOrUpdateCategory(null))
            },
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            Text("Click")
        }
    }
}