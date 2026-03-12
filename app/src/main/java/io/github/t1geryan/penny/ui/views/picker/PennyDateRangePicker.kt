package io.github.t1geryan.penny.ui.views.picker

import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.DatePickerDialog
import androidx.compose.material3.DateRangePicker
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.material3.getSelectedEndDate
import androidx.compose.material3.getSelectedStartDate
import androidx.compose.material3.rememberDateRangePickerState
import androidx.compose.runtime.Composable
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.t1geryan.penny.R
import kotlinx.datetime.LocalDateRange
import kotlinx.datetime.toKotlinLocalDate

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun PennyDateRangePicker(
    onDismissRequest: () -> Unit,
    onRangeSelected: (LocalDateRange) -> Unit,
    modifier: Modifier = Modifier,
    dismissButtonTitle: String = stringResource(R.string.common_dialog_button_cancel),
    confirmButtonTitle: String = stringResource(R.string.common_dialog_button_ok),
) {
    val state = rememberDateRangePickerState(
        initialSelectedStartDate = null,
        initialSelectedEndDate = null,
        initialDisplayedMonth = null,
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
                enabled = state.getSelectedStartDate() != null && state.getSelectedEndDate() != null,
                onClick = {
                    val start = state.getSelectedStartDate()
                    val end = state.getSelectedEndDate()
                    if (start == null || end == null) {
                        // Should not be reachable
                        return@TextButton
                    }

                    onRangeSelected(
                        LocalDateRange(
                            start.toKotlinLocalDate(),
                            end.toKotlinLocalDate(),
                        ),
                    )
                    onDismissRequest()
                },
            ) {
                Text(confirmButtonTitle)
            }
        },
        modifier = modifier,
    ) {
        DateRangePicker(
            state = state,
            showModeToggle = false,
        )
    }
}
