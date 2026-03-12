package io.github.t1geryan.penny.ui.views.dialog

import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.material3.AlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonColors
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import io.github.t1geryan.penny.R

@Composable
fun ConfirmationDialog(
    onDismissRequest: () -> Unit,
    onConfirm: () -> Unit,
    title: String,
    modifier: Modifier = Modifier,
    description: String? = null,
    confirmButtonTitle: String = stringResource(R.string.common_dialog_button_confirm),
    confirmButtonColors: ButtonColors = ButtonDefaults.buttonColors(
        containerColor = MaterialTheme.colorScheme.primary,
        contentColor = MaterialTheme.colorScheme.onPrimary,
    ),
    confirmButtonEnabled: Boolean = true,
    cancelButtonTitle: String = stringResource(R.string.common_dialog_button_cancel),
    cancelButtonColors: ButtonColors = ButtonDefaults.textButtonColors(
        containerColor = MaterialTheme.colorScheme.secondaryContainer,
        contentColor = MaterialTheme.colorScheme.onSecondaryContainer,
    ),
    cancelButtonEnabled: Boolean = true,
    properties: DialogProperties = DialogProperties(),
    containerColor: Color = MaterialTheme.colorScheme.surfaceContainer,
    titleColor: Color = MaterialTheme.colorScheme.onSurface,
    descriptionColor: Color = MaterialTheme.colorScheme.onSurface,
) {
    AlertDialog(
        properties = properties,
        confirmButton = {
            TextButton(
                onClick = {
                    onConfirm()
                    onDismissRequest()
                },
                colors = confirmButtonColors,
                enabled = confirmButtonEnabled,
                modifier = Modifier.fillMaxWidth(0.5f),
            ) {
                Text(confirmButtonTitle)
            }
        },
        dismissButton = {
            Button(
                onClick = onDismissRequest,
                colors = cancelButtonColors,
                enabled = cancelButtonEnabled,
            ) {
                Text(cancelButtonTitle)
            }
        },
        modifier = modifier,
        onDismissRequest = onDismissRequest,
        title = {
            Text(title)
        },
        text = description?.let {
            {
                Text(it)
            }
        },
        containerColor = containerColor,
        titleContentColor = titleColor,
        textContentColor = descriptionColor,
    )
}
