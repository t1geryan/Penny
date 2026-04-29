package io.github.t1geryan.penny.ui.features.category

import androidx.compose.foundation.BorderStroke
import androidx.compose.foundation.background
import androidx.compose.foundation.border
import androidx.compose.foundation.clickable
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.FlowRow
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.RowScope
import androidx.compose.foundation.layout.WindowInsets
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.heightIn
import androidx.compose.foundation.layout.navigationBars
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.layout.widthIn
import androidx.compose.foundation.layout.windowInsetsPadding
import androidx.compose.foundation.rememberScrollState
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.foundation.verticalScroll
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.Card
import androidx.compose.material3.CardDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.ElevatedCard
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.material3.TextField
import androidx.compose.material3.TextFieldDefaults
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.clip
import androidx.compose.ui.graphics.Color
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.unit.dp
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.penny.R
import io.github.t1geryan.penny.ui.contracts.humanReadableName
import io.github.t1geryan.penny.ui.contracts.predefinedColors
import io.github.t1geryan.penny.ui.contracts.predefinedEmojis
import io.github.t1geryan.penny.ui.views.core.ComponentWithTopBar
import io.github.t1geryan.penny.ui.views.core.DefaultBackButton
import io.github.t1geryan.penny.ui.views.icon.TextIcon
import io.github.t1geryan.penny.ui.views.picker.BottomSheetSingleItemPicker
import io.github.t1geryan.penny.ui.views.spacing.Expanded
import io.github.t1geryan.penny.ui.views.spacing.Spacer
import io.github.t1geryan.theme.cornerRadius
import io.github.t1geryan.theme.icons
import io.github.t1geryan.theme.spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@Composable
fun CreateOrEditCategoryComponent(
    state: CreateOrEditCategoryState,
    onSendIntent: (CreateOrEditCategoryIntent) -> Unit,
    eventsFlow: Flow<CreateOrEditCategoryEvent>,
    onNavigateUp: () -> Unit,
    modifier: Modifier = Modifier,
) {
    ComponentWithTopBar(
        title = stringResource(
            if (state.isEditing) R.string.screen_create_or_edit_category_edit_title
            else R.string.screen_create_or_edit_category_create_title,
        ),
        backButton = {
            DefaultBackButton(
                onClick = {
                    onSendIntent(CreateOrEditCategoryIntent.NavigateUp)
                },
            )
        },
        modifier = modifier,
    ) {
        Box(
            contentAlignment = Alignment.Center,
            modifier = Modifier
                .fillMaxSize()
                .windowInsetsPadding(WindowInsets.navigationBars),
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

    CreateOrEditCategoryDialog(dialogState = state.dialogState, onSendIntent = onSendIntent)

    LaunchedEffect(Unit) {
        eventsFlow.collectLatest { event ->
            when (event) {
                CreateOrEditCategoryEvent.NavigateUp -> onNavigateUp()
            }
        }
    }
}

@Composable
private fun Content(
    state: CreateOrEditCategoryState,
    onSendIntent: (CreateOrEditCategoryIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        NameCard(
            state = state,
            onSendIntent = onSendIntent,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(MaterialTheme.spacing.medium)
        CurrencyCard(
            state = state,
            onSendIntent = onSendIntent,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(MaterialTheme.spacing.medium)
        ColorCard(
            state = state,
            onSendIntent = onSendIntent,
            modifier = Modifier.fillMaxWidth(),
        )
        Spacer(MaterialTheme.spacing.medium)
        IconCard(
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
private fun NameCard(
    state: CreateOrEditCategoryState,
    onSendIntent: (CreateOrEditCategoryIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = modifier,
    ) {
        FieldTitle(
            title = stringResource(R.string.screen_create_or_edit_category_fill_name_title),
            icon = MaterialTheme.icons.description,
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
        )
        TextField(
            value = state.enteredName,
            onValueChange = { onSendIntent(CreateOrEditCategoryIntent.SetName(it)) },
            placeholder = {
                Text(stringResource(R.string.screen_create_or_edit_category_fill_name_hint))
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
        Spacer(MaterialTheme.spacing.medium)
    }
}

@Composable
private fun CurrencyCard(
    state: CreateOrEditCategoryState,
    onSendIntent: (CreateOrEditCategoryIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = modifier,
    ) {
        FieldTitle(
            title = stringResource(R.string.screen_create_or_edit_category_fill_currency),
            icon = MaterialTheme.icons.label,
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
        )
        PickerField(
            onClick = { onSendIntent(CreateOrEditCategoryIntent.PickCurrency) },
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium)
                .heightIn(min = 48.dp),
        ) {
            Text(state.selectedCurrency.symbol)
            Spacer(MaterialTheme.spacing.normal)
            Text(state.selectedCurrency.humanReadableName)
        }
        Spacer(MaterialTheme.spacing.medium)
    }
}

@Composable
private fun ColorCard(
    state: CreateOrEditCategoryState,
    onSendIntent: (CreateOrEditCategoryIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = modifier,
    ) {
        FieldTitle(
            title = stringResource(R.string.screen_create_or_edit_category_fill_color),
            icon = MaterialTheme.icons.label,
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        ) {
            predefinedColors.forEach { color ->
                ColorItem(
                    color = color,
                    isSelected = state.selectedColor == color,
                    onClick = { onSendIntent(CreateOrEditCategoryIntent.SetColor(color)) },
                )
            }
        }
        Spacer(MaterialTheme.spacing.medium)
    }
}

@Composable
private fun IconCard(
    state: CreateOrEditCategoryState,
    onSendIntent: (CreateOrEditCategoryIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    ElevatedCard(
        colors = CardDefaults.elevatedCardColors(
            containerColor = MaterialTheme.colorScheme.surfaceContainer,
        ),
        modifier = modifier,
    ) {
        FieldTitle(
            title = stringResource(R.string.screen_create_or_edit_category_fill_icon_title),
            icon = MaterialTheme.icons.description,
            modifier = Modifier.padding(MaterialTheme.spacing.medium),
        )
        FlowRow(
            modifier = Modifier
                .fillMaxWidth()
                .padding(horizontal = MaterialTheme.spacing.medium),
            horizontalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
            verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
        ) {
            predefinedEmojis.forEach { emoji ->
                EmojiItem(
                    emoji = emoji,
                    isSelected = state.selectedEmoji == emoji,
                    onClick = { onSendIntent(CreateOrEditCategoryIntent.SetEmoji(emoji)) },
                )
            }
        }
        Spacer(MaterialTheme.spacing.medium)
    }
}

@Composable
private fun ColorItem(
    color: Long,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(MaterialTheme.cornerRadius.medium))
            .background(Color(color))
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(MaterialTheme.cornerRadius.medium),
            )
            .clickable(onClick = onClick),
    )
}

@Composable
private fun EmojiItem(
    emoji: String,
    isSelected: Boolean,
    onClick: () -> Unit,
) {
    Box(
        modifier = Modifier
            .size(48.dp)
            .clip(RoundedCornerShape(MaterialTheme.cornerRadius.medium))
            .background(
                if (isSelected) MaterialTheme.colorScheme.primaryContainer
                else MaterialTheme.colorScheme.surfaceVariant,
            )
            .border(
                width = if (isSelected) 2.dp else 0.dp,
                color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                shape = RoundedCornerShape(MaterialTheme.cornerRadius.medium),
            )
            .clickable(onClick = onClick),
        contentAlignment = Alignment.Center,
    ) {
        Text(emoji, style = MaterialTheme.typography.headlineSmall)
    }
}

@Composable
private fun SaveButton(
    state: CreateOrEditCategoryState,
    onSendIntent: (CreateOrEditCategoryIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledTonalButton(
        onClick = { onSendIntent(CreateOrEditCategoryIntent.SaveCategory) },
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
                    R.string.screen_create_or_edit_category_save_button_title_edit
                } else {
                    R.string.screen_create_or_edit_category_save_button_title_create
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

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun CreateOrEditCategoryDialog(
    dialogState: CreateOrEditCategoryDialogState,
    onSendIntent: (CreateOrEditCategoryIntent) -> Unit,
) {
    when (dialogState) {
        is CreateOrEditCategoryDialogState.SelectCurrencyDialog -> BottomSheetSingleItemPicker(
            onDismissRequest = { onSendIntent(CreateOrEditCategoryIntent.DismissDialog) },
            items = Currency.USER_LIST,
            selectedItem = dialogState.initialCurrency,
            key = { it.code },
            title = stringResource(R.string.screen_create_or_edit_category_fill_currency),
        ) { item, isSelected ->
            Card(
                onClick = {
                    onSendIntent(CreateOrEditCategoryIntent.SetCurrency(item))
                    onSendIntent(CreateOrEditCategoryIntent.DismissDialog)
                },
                colors = CardDefaults.cardColors(
                    containerColor = if (isSelected) {
                        MaterialTheme.colorScheme.primaryContainer
                    } else {
                        MaterialTheme.colorScheme.surfaceContainer
                    },
                    contentColor = MaterialTheme.colorScheme.onPrimaryContainer,
                ),
                border = BorderStroke(
                    width = 2.dp,
                    color = if (isSelected) MaterialTheme.colorScheme.primary else Color.Transparent,
                ),
                shape = RoundedCornerShape(MaterialTheme.cornerRadius.large),
                modifier = Modifier
                    .fillMaxWidth()
                    .padding(MaterialTheme.spacing.normal),
            ) {
                Row(
                    modifier = Modifier
                        .fillMaxWidth()
                        .padding(MaterialTheme.spacing.medium),
                ) {
                    TextIcon(
                        text = item.symbol,
                        backgroundColor = MaterialTheme.colorScheme.surfaceVariant,
                    )
                    Spacer(MaterialTheme.spacing.normal)
                    Column {
                        Text(item.humanReadableName, style = MaterialTheme.typography.titleMedium)
                        Spacer(MaterialTheme.spacing.tiny)
                        Text(item.code, style = MaterialTheme.typography.bodyMedium)
                    }
                }
            }
        }

        CreateOrEditCategoryDialogState.None -> {}
    }
}
