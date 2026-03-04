package io.github.t1geryan.penny.ui.navigation.graph

import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.material3.Button
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableIntStateOf
import androidx.compose.runtime.saveable.rememberSaveable
import androidx.compose.runtime.setValue
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.navigation.NavController
import androidx.navigation.NavGraphBuilder
import androidx.navigation.NavHostController
import androidx.navigation.compose.NavHost
import androidx.navigation.compose.composable
import androidx.navigation.compose.rememberNavController
import io.github.t1geryan.navigation.TabsNavEntry

@Composable
fun TabsNavGraph(
    modifier: Modifier = Modifier,
    rootNavController: NavController,
    tabsNavController: NavHostController = rememberNavController(),
) {
    NavHost(
        navController = tabsNavController,
        startDestination = TabsNavEntry.INITIAL,
        modifier = modifier,
    ) {
        composeCategories()
        composeTransaction()
        composeNotifications()
        composeStatistics()
    }
}

fun NavGraphBuilder.composeCategories() {
    composable<TabsNavEntry.Categories> {
        Stub()
    }
}

fun NavGraphBuilder.composeTransaction() {
    composable<TabsNavEntry.Transactions> {
        Stub()
    }
}

fun NavGraphBuilder.composeNotifications() {
    composable<TabsNavEntry.Notifications> {
        Stub()
    }
}

fun NavGraphBuilder.composeStatistics() {
    composable<TabsNavEntry.Statistics> {
        Stub()
    }
}

@Composable
private fun Stub() {
    var a by rememberSaveable { mutableIntStateOf(0) }
    Box(
        modifier = Modifier
            .fillMaxSize()
            .background(Color.Green),
    ) {
        Text("$a", modifier = Modifier.align(Alignment.TopCenter))
        Button(
            onClick = {
                a += 1
            },
            modifier = Modifier.align(Alignment.BottomCenter),
        ) {
            Text("Click")
        }
    }
}