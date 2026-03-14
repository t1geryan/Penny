package io.github.t1geryan.penny.ui.views.core

import androidx.compose.foundation.interaction.MutableInteractionSource
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.statusBars
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TopAppBar
import androidx.compose.material3.TopAppBarDefaults
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import io.github.t1geryan.penny.R
import io.github.t1geryan.theme.icons

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun ComponentWithTopBar(
    title: String,
    modifier: Modifier = Modifier,
    backButton: @Composable () -> Unit = {},
    content: @Composable () -> Unit,
) {
    Column(
        modifier = modifier.fillMaxWidth(),
    ) {
        TopAppBar(
            title = {
                Text(
                    title,
                    style = MaterialTheme.typography.headlineMedium,
                )
            },
            navigationIcon = {
                backButton()
            },
            colors = TopAppBarDefaults.topAppBarColors().copy(
                containerColor = MaterialTheme.colorScheme.primaryContainer,
                titleContentColor = MaterialTheme.colorScheme.onPrimaryContainer,
            ),
            windowInsets = WindowInsets.statusBars,
        )

        content()
    }
}

@Composable
fun DefaultBackButton(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    interactionSource: MutableInteractionSource? = null,
    tint: Color = MaterialTheme.colorScheme.onPrimaryContainer,
) {
    IconButton(
        onClick = onClick,
        enabled = enabled,
        interactionSource = interactionSource,
        modifier = modifier,
    ) {
        Icon(
            MaterialTheme.icons.arrowBack,
            contentDescription = stringResource(R.string.common_cd_back_button),
            tint = tint,
        )
    }
}
