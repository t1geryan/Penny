package io.github.t1geryan.penny.ui.views.picker

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePicker
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.getSelectedDate
import androidx.compose.material3.rememberDatePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.t1geryan.penny.R
import io.github.t1geryan.penny.ui.utils.toMillis
import kotlinx.datetime.LocalDate
import kotlinx.datetime.toKotlinLocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PennyDatePicker(
    onDismissRequest: () -> Unit,
    onDateSelected: (LocalDate) -> Unit,
    modifier: Modifier = Modifier,
    onConfirmClicked: () -> Unit = onDismissRequest,
    initialSelected: LocalDate? = null,
    dismissButtonTitle: String = stringResource(R.string.common_dialog_button_cancel),
    confirmButtonTitle: String = stringResource(R.string.common_dialog_button_ok),
) {
    val state = rememberDatePickerState(
        initialSelectedDateMillis = initialSelected?.toMillis(),
    )

    DatePickerDialog(
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
                enabled = state.getSelectedDate() != null,
                onClick = {
                    state.getSelectedDate()?.let {
                        onDateSelected(it.toKotlinLocalDate())
                    }

                    onConfirmClicked()
                },
            ) {
                Text(confirmButtonTitle)
            }
        },
        modifier = modifier,
    ) {
        DatePicker(
            state = state,
            showModeToggle = false,
        )
    }
}
