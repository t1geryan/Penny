package io.github.t1geryan.penny.ui.navigation.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation3.runtime.NavBackStack
import androidx.navigation3.runtime.NavEntry
import androidx.navigation3.runtime.NavKey
import io.github.t1geryan.navigation.RootNavEntry
import io.github.t1geryan.penny.ui.features.tabs.TabsComponent

fun provideRootGraph(backStack: NavBackStack<NavKey>, key: RootNavEntry): NavEntry<NavKey> =
    when (key) {
        is RootNavEntry.CreateOrUpdateCategory -> NavEntry(key) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Cyan),
            )
        }

        is RootNavEntry.CreateOrUpdateTransaction -> NavEntry(key) {
            Box(
                modifier = Modifier
                    .fillMaxSize()
                    .background(Color.Magenta),
            )
        }

        RootNavEntry.Tabs -> NavEntry(key) {
            TabsComponent(backStack, modifier = Modifier.fillMaxSize())
        }
    }