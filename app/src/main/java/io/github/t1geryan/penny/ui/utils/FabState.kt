package io.github.t1geryan.penny.ui.utils

import androidx.compose.material3.FabPosition
import androidx.compose.runtime.Composable
import androidx.compose.runtime.compositionLocalOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.mutableStateOf
import androidx.compose.runtime.remember
import androidx.compose.runtime.setValue
import androidx.compose.ui.graphics.vector.ImageVector

@Composable
fun rememberFabState(
    icon: ImageVector? = FabTokens.DEFAULT_ICON,
    contentDescription: String? = FabTokens.DEFAULT_CONTENT_DESCRIPTION,
    onClick: () -> Unit = FabTokens.DEFAULT_ACTION,
    position: FabPosition = FabTokens.DEFAULT_POSITION,
): FabState {
    return remember {
        FabState().apply { setFab(icon, contentDescription, onClick, position) }
    }
}

val LocalFab = compositionLocalOf { FabState() }

class FabState {
    var icon: ImageVector? by mutableStateOf(FabTokens.DEFAULT_ICON)
        private set
    var contentDescription: String? by mutableStateOf(FabTokens.DEFAULT_CONTENT_DESCRIPTION)
        private set
    var onClick: () -> Unit = FabTokens.DEFAULT_ACTION
        private set

    var position: FabPosition by mutableStateOf(FabTokens.DEFAULT_POSITION)

    fun setFab(
        icon: ImageVector?,
        contentDescription: String?,
        onClick: () -> Unit,
        position: FabPosition = FabTokens.DEFAULT_POSITION,
    ) {
        this.icon = icon
        this.contentDescription = contentDescription
        this.onClick = onClick
        this.position = position
    }

    fun clearFab() {
        icon = FabTokens.DEFAULT_ICON
        contentDescription = FabTokens.DEFAULT_CONTENT_DESCRIPTION
        onClick = FabTokens.DEFAULT_ACTION
        position = FabTokens.DEFAULT_POSITION
    }
}

private object FabTokens {
    val DEFAULT_ICON: ImageVector? = null
    val DEFAULT_CONTENT_DESCRIPTION: String? = null
    val DEFAULT_ACTION = { }
    val DEFAULT_POSITION = FabPosition.End
}
