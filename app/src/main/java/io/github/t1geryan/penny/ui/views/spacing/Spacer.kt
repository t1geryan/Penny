package io.github.t1geryan.penny.ui.views.spacing

import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.height
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.width
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.unit.Dp

@Composable
fun Spacer(spacing: Dp) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.size(spacing))

@Composable
fun RowScope.Spacer(spacing: Dp) = androidx.compose.foundation.layout.Spacer(
    modifier = Modifier.width(spacing).height(Dp.Hairline),
)

@Composable
fun ColumnScope.Spacer(spacing: Dp) = androidx.compose.foundation.layout.Spacer(
    modifier = Modifier.height(spacing).width(Dp.Hairline),
)

@Composable
fun RowScope.Expanded(weight: Float = 1.0f) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(weight = weight))

@Composable
fun ColumnScope.Expanded(weight: Float = 1.0f) =
    androidx.compose.foundation.layout.Spacer(modifier = Modifier.weight(weight = weight))
