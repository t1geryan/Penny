package io.github.t1geryan.penny.ui.features.createtransaction

import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.BoxWithConstraints
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.width
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.text.KeyboardOptions
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedTextField
import androidx.compose.material3.OutlinedTextFieldDefaults
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.input.KeyboardType
import androidx.compose.ui.unit.dp
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.models.Alpha
import io.github.t1geryan.models.Percent
import io.github.t1geryan.penny.R
import io.github.t1geryan.penny.ui.contracts.InputFilters
import io.github.t1geryan.penny.ui.contracts.QuickAmount
import io.github.t1geryan.penny.ui.contracts.format
import io.github.t1geryan.penny.ui.contracts.quickAmounts
import io.github.t1geryan.penny.ui.views.category.CategoryIcon
import io.github.t1geryan.penny.ui.views.core.ComponentWithTopBar
import io.github.t1geryan.penny.ui.views.core.DefaultBackButton
import io.github.t1geryan.penny.ui.views.picker.CategoriesPicker
import io.github.t1geryan.penny.ui.views.picker.PennyDatePicker
import io.github.t1geryan.penny.ui.views.picker.PennyTimePicker
import io.github.t1geryan.penny.ui.views.spacing.Expanded
import io.github.t1geryan.penny.ui.views.spacing.Spacer
import io.github.t1geryan.theme.cornerRadius
import io.github.t1geryan.theme.icons
import io.github.t1geryan.theme.spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest
import kotlinx.datetime.LocalDateTime

@Composable
fun CreateOrUpdateTransactionComponent(
    state: CreateOrUpdateTransactionState,
    onSendIntent: (CreateOrUpdateTransactionIntent) -> Unit,
    eventsFlow: Flow<CreateOrUpdateTransactionEvent>,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ComponentWithTopBar(
        title = stringResource(
            if (state.isEditing) {
                R.string.screen_create_or_update_transaction_update_title
            } else {
                R.string.screen_create_or_update_transaction_create_title
            },
        ),
        backButton = {
            DefaultBackButton(onClick = onNavigateUp)
        },
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier.fillMaxSize(),
        ) {
            Content(
                state = state,
                onSendIntent = onSendIntent,
                modifier = Modifier
                    .fillMaxSize()
                    .verticalScroll(rememberScrollState())
                    .padding(MaterialTheme.spacing.medium),
            )

            if (state.isLoading) {
                CircularProgressIndicator()
            }
        }
    }

    CreateOrUpdateTransactionDialog(dialogState = state.dialogState, onSendIntent = onSendIntent)

    LaunchedEffect(Unit) {
        eventsFlow.collectLatest { event ->
            when (event) {
                CreateOrUpdateTransactionEvent.NavigateUp -> onNavigateUp()
            }
        }
    }
}

@Composable
private fun Content(
    state: CreateOrUpdateTransactionState,
    onSendIntent: (CreateOrUpdateTransactionIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        AmountCard(
            state = state,
            onSendIntent = onSendIntent,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(MaterialTheme.spacing.medium)
        MainCard(
            state = state,
            onSendIntent = onSendIntent,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(MaterialTheme.spacing.medium)
        SaveButton(
            state = state,
            onSendIntent = onSendIntent,
            modifier = Modifier
                .widthIn(max = 320.dp)
                .fillMaxWidth(),
        )
    }
}

@Composable
private fun AmountCard(
    state: CreateOrUpdateTransactionState,
    onSendIntent: (CreateOrUpdateTransactionIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = modifier,
    ) {
        FieldTitle(
            title = stringResource(R.string.screen_create_or_update_transaction_fill_category_title),
            icon = MaterialTheme.icons.label,
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
        )
        PickerField(
            onClick = { onSendIntent(CreateOrUpdateTransactionIntent.PickCategory) },
            enabled = state.isEditing.not(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium)
                .heightIn(min = 48.dp),
        ) {
            state.selectedCategory?.let { category ->
                CategoryIcon(category)
                Spacer(MaterialTheme.spacing.normal)
            }
            Text(
                state.selectedCategory?.name
                    ?: stringResource(R.string.screen_create_or_update_transaction_fill_category_hint),
            )
        }
        Spacer(MaterialTheme.spacing.large)
        Text(
            stringResource(R.string.screen_create_or_update_transaction_fill_amount_title),
            style = MaterialTheme.typography.titleSmall,
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
        )
        OutlinedTextField(
            value = state.enteredAmount,
            onValueChange = {
                val filtered = InputFilters.cost.filter(it)
                onSendIntent(CreateOrUpdateTransactionIntent.SetAmount(filtered))
            },
            singleLine = true,
            keyboardOptions = KeyboardOptions(
                keyboardType = KeyboardType.Decimal,
            ),
            placeholder = {
                Text(stringResource(R.string.screen_create_or_update_transaction_fill_amount_hint))
            },
            enabled = state.selectedCurrency != null,
            prefix = {
                Text(
                    state.selectedCurrency?.symbol.orEmpty(),
                    style = MaterialTheme.typography.titleLarge.copy(
                        color = if (state.isAmountValid) {
                            MaterialTheme.colorScheme.onSurfaceVariant
                        } else {
                            MaterialTheme.colorScheme.onErrorContainer
                        },
                    ),
                    modifier = Modifier.alpha(
                        if (state.selectedCurrency == null) Alpha.TRANSPARENT.value else Alpha.OPAQUE.value,
                    ),
                )
            },
            colors = OutlinedTextFieldDefaults.colors(
                focusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                unfocusedBorderColor = MaterialTheme.colorScheme.onPrimaryContainer,
                errorBorderColor = MaterialTheme.colorScheme.onErrorContainer,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                errorContainerColor = MaterialTheme.colorScheme.errorContainer,
                focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorTextColor = MaterialTheme.colorScheme.onErrorContainer,
            ),
            isError = state.isAmountValid.not(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium),
        )
        state.selectedCurrency?.takeIf { state.isEditing.not() }?.let { currency ->
            Spacer(MaterialTheme.spacing.medium)
            QuickAmounts(
                selectedCurrency = currency,
                onQuickAmountSelected = { onSendIntent(CreateOrUpdateTransactionIntent.SetQuickAmount(it)) },
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(horizontal = MaterialTheme.spacing.medium),
            )
        }
        Spacer(MaterialTheme.spacing.medium)
    }
}

@Composable
private fun MainCard(
    state: CreateOrUpdateTransactionState,
    onSendIntent: (CreateOrUpdateTransactionIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = modifier,
    ) {
        FieldTitle(
            title = stringResource(R.string.screen_create_or_update_transaction_fill_name_title),
            icon = MaterialTheme.icons.description,
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
        )
        TextField(
            value = state.enteredName,
            onValueChange = { onSendIntent(CreateOrUpdateTransactionIntent.SetName(it)) },
            placeholder = {
                Text(stringResource(R.string.screen_create_or_update_transaction_fill_name_hint))
            },
            shape = RoundedCornerShape(MaterialTheme.cornerRadius.large),
            colors = TextFieldDefaults.colors(
                focusedIndicatorColor = Color.Transparent,
                errorIndicatorColor = Color.Transparent,
                unfocusedIndicatorColor = Color.Transparent,
                focusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                unfocusedTextColor = MaterialTheme.colorScheme.onSurfaceVariant,
                errorTextColor = MaterialTheme.colorScheme.onErrorContainer,
                focusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                unfocusedContainerColor = MaterialTheme.colorScheme.surfaceVariant,
                errorContainerColor = MaterialTheme.colorScheme.errorContainer,
            ),
            singleLine = true,
            isError = state.isNameValid.not(),
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium),
        )
        Spacer(MaterialTheme.spacing.large)
        FieldTitle(
            title = stringResource(R.string.screen_create_or_update_transaction_fill_date_title),
            icon = MaterialTheme.icons.calendar,
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
        )
        PickerField(
            onClick = { onSendIntent(CreateOrUpdateTransactionIntent.PickDate) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium)
                .heightIn(min = 48.dp),
        ) {
            Text(
                state.selectedDate?.format(LocalContext.current)
                    ?: stringResource(R.string.screen_create_or_update_transaction_fill_date_hint),
            )
        }
        Spacer(MaterialTheme.spacing.medium)
    }
}

@Composable
private fun SaveButton(
    state: CreateOrUpdateTransactionState,
    onSendIntent: (CreateOrUpdateTransactionIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledTonalButton(
        onClick = { onSendIntent(CreateOrUpdateTransactionIntent.SaveTransaction) },
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.primary,
            contentColor = MaterialTheme.colorScheme.onPrimary,
        ),
        contentPadding = PaddingValues(
            MaterialTheme.spacing.normal,
        ),
        shape = RoundedCornerShape(MaterialTheme.cornerRadius.large),
        enabled = state.isAllowedToSave,
        modifier = modifier,
    ) {
        Text(
            stringResource(
                if (state.isEditing) {
                    R.string.screen_create_or_update_transaction_save_button_title_edit
                } else {
                    R.string.screen_create_or_update_transaction_save_button_title_create
                },
            ),
        )
    }
}

@Composable
private fun FieldTitle(
    title: String,
    icon: ImageVector,
    modifier: Modifier = Modifier,
) {
    Row(
        modifier = modifier,
    ) {
        Icon(icon, tint = MaterialTheme.colorScheme.onSurface, contentDescription = null)
        Spacer(MaterialTheme.spacing.extraSmall)
        Text(title, style = MaterialTheme.typography.titleSmall)
    }
}

@Composable
private fun PickerField(
    onClick: () -> Unit,
    modifier: Modifier = Modifier,
    enabled: Boolean = true,
    content: @Composable RowScope.() -> Unit,
) {
    FilledTonalButton(
        onClick = onClick,
        modifier = modifier,
        colors = ButtonDefaults.filledTonalButtonColors(
            containerColor = MaterialTheme.colorScheme.surfaceVariant,
            contentColor = MaterialTheme.colorScheme.onSurfaceVariant,
        ),
        enabled = enabled,
        shape = RoundedCornerShape(MaterialTheme.cornerRadius.large),
    ) {
        content()
        Expanded()
        Icon(
            MaterialTheme.icons.dropdown,
            contentDescription = null,
        )
    }
}

@Composable
private fun QuickAmounts(
    selectedCurrency: Currency,
    onQuickAmountSelected: (QuickAmount) -> Unit,
    modifier: Modifier = Modifier,
) {
    FlowRow(
        modifier = modifier,
        horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.extraSmall),
    ) {
        selectedCurrency.quickAmounts.forEach { quickAmount ->
            Button(
                onClick = { onQuickAmountSelected(quickAmount) },
                colors = ButtonDefaults.filledTonalButtonColors(
                    containerColor = MaterialTheme.colorScheme.primaryContainer,
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                shape = RoundedCornerShape(MaterialTheme.cornerRadius.large),
                modifier = Modifier.widthIn(min = 64.dp),
            ) {
                Text(quickAmount.format(LocalContext.current), maxLines = 1)
            }
        }
    }
}

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrUpdateTransactionDialog(
    dialogState: CreateOrUpdateTransactionDialogState,
    onSendIntent: (CreateOrUpdateTransactionIntent) -> Unit,
) {
    when (dialogState) {
        is CreateOrUpdateTransactionDialogState.CategoryPickerDialog -> BoxWithConstraints {
            CategoriesPicker(
                title = stringResource(R.string.screen_create_or_update_transaction_fill_category_hint),
                categories = dialogState.categories,
                initialSelectedCategories = dialogState.initialSelected?.let { listOf(it) }
                    ?: emptyList(),
                onDismissRequest = {
                    onSendIntent(CreateOrUpdateTransactionIntent.DismissDialog)
                },
                onCategoriesSelected = { categories ->
                    require(categories.size == 1)
                    onSendIntent(CreateOrUpdateTransactionIntent.SetCategory(categories.first()))
                },
                modifier = Modifier
                    .heightIn(max = this.maxHeight * Percent.THREE_QUARTERS.fraction)
                    .width(this.maxWidth - MaterialTheme.spacing.medium * 2),
                maxSelectableItems = 1U,
                minSelectableItems = 1U,
            )
        }
        is CreateOrUpdateTransactionDialogState.DatePickerDialog -> PennyDatePicker(
            onDismissRequest = { onSendIntent(CreateOrUpdateTransactionIntent.DismissDialog) },
            onDateSelected = { date -> onSendIntent(CreateOrUpdateTransactionIntent.PickTime(date)) },
            onConfirmClicked = {},
            initialSelected = dialogState.initialSelected,
        )

        is CreateOrUpdateTransactionDialogState.TimePickerDialog -> PennyTimePicker(
            onDismissRequest = { onSendIntent(CreateOrUpdateTransactionIntent.DismissDialog) },
            onTimeSelected = { time ->
                onSendIntent(
                    CreateOrUpdateTransactionIntent.SetDate(LocalDateTime(dialogState.date, time)),
                )
            },
        )

        CreateOrUpdateTransactionDialogState.None -> {}
    }
}
