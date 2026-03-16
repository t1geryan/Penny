package io.github.t1geryan.penny.ui.views.picker

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.TimePicker
import androidx.compose.material3.TimePickerDialog
import androidx.compose.material3.TimePickerLayoutType
import androidx.compose.material3.rememberTimePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.t1geryan.penny.R
import kotlinx.datetime.LocalTime

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PennyTimePicker(
    onDismissRequest: () -> Unit,
    onTimeSelected: (LocalTime) -> Unit,
    modifier: Modifier = Modifier,
    onConfirmClicked: () -> Unit = onDismissRequest,
    initialSelected: LocalTime? = null,
    title: String = stringResource(R.string.common_dialog_time_picker_title),
    dismissButtonTitle: String = stringResource(R.string.common_dialog_button_cancel),
    confirmButtonTitle: String = stringResource(R.string.common_dialog_button_ok),
) {
    val state = rememberTimePickerState(
        initialHour = initialSelected?.hour ?: 0,
        initialMinute = initialSelected?.minute ?: 0,
    )

    TimePickerDialog(
        title = {
            Text(title, style = MaterialTheme.typography.titleMedium)
        },
        onDismissRequest = onDismissRequest,
        dismissButton = {
            TextButton(
                onClick = onDismissRequest,
                colors = ButtonDefaults.textButtonColors(
                    contentColor = MaterialTheme.colorScheme.outline,
                ),
            ) {
                Text(dismissButtonTitle)
            }
        },
        confirmButton = {
            TextButton(
                onClick = {
                    onTimeSelected(LocalTime(state.hour, state.minute))
                    onConfirmClicked()
                },
            ) {
                Text(confirmButtonTitle)
            }
        },
        modifier = modifier,
    ) {
        TimePicker(
            state = state,
            layoutType = TimePickerLayoutType.Vertical,
        )
    }
}
