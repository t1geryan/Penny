package io.github.t1geryan.penny.ui.features.categories

import android.annotation.SuppressLint
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Box
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.material3.Button
import androidx.compose.material3.ButtonDefaults
import androidx.compose.material3.CircularProgressIndicator
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import androidx.lifecycle.compose.LifecycleResumeEffect
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.domain.models.calculateSpentAmount
import io.github.t1geryan.models.Percent
import io.github.t1geryan.penny.R
import io.github.t1geryan.penny.ui.utils.LocalFab
import io.github.t1geryan.penny.ui.views.category.CategoryItem
import io.github.t1geryan.penny.ui.views.core.ComponentWithTopBar
import io.github.t1geryan.penny.ui.views.dialog.ConfirmationDialog
import io.github.t1geryan.penny.ui.views.spacing.Spacer
import io.github.t1geryan.theme.icons
import io.github.t1geryan.theme.spacing
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.collectLatest

@SuppressLint("LocalContextGetResourceValueCall")
@Composable
fun CategoriesComponent(
    state: CategoriesState,
    onSendIntent: (CategoriesIntent) -> Unit,
    eventsFlow: Flow<CategoriesEvent>,
    onNavigateToCreateOrEditCategory: (CategoryId?) -> Unit,
    modifier: Modifier = Modifier,
) {
    ComponentWithTopBar(
        title = stringResource(R.string.screen_categories_title),
        modifier = modifier,
    ) {
        Content(
            state = state,
            onSendIntent = onSendIntent,
            modifier = Modifier.fillMaxSize(),
        )
    }

    CategoriesDialog(state.dialogState, onSendIntent)

    LaunchedEffect(Unit) {
        eventsFlow.collectLatest { event ->
            when (event) {
                is CategoriesEvent.NavigateToCreateOrEditCategory -> onNavigateToCreateOrEditCategory(event.categoryId)
            }
        }
    }

    val fabState = LocalFab.current
    val context = LocalContext.current
    val fabIcon = MaterialTheme.icons.add
    LifecycleResumeEffect(Unit) {
        fabState.setFab(
            icon = fabIcon,
            contentDescription = context.getString(R.string.common_cd_add_category),
            onClick = { onSendIntent(CategoriesIntent.NavigateToCreteOrEditCategory()) },
        )

        onPauseOrDispose {
            fabState.clearFab()
        }
    }
}

@Composable
private fun Content(
    state: CategoriesState,
    onSendIntent: (CategoriesIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    if (state.isEmpty) {
        EmptyContent(
            onAddCategoryClicked = {
                onSendIntent(CategoriesIntent.NavigateToCreteOrEditCategory())
            },
            modifier = modifier,
        )
    } else {
        CategoriesList(
            categories = state.categories,
            lastMonthTransactions = state.thisMonthTransactions,
            onSendIntent = onSendIntent,
            modifier = modifier,
            isLoading = state.isLoading,
        )
    }
}

@Composable
private fun EmptyContent(
    onAddCategoryClicked: () -> Unit,
    modifier: Modifier = Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Icon(
            MaterialTheme.icons.category,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                .padding(MaterialTheme.spacing.normal),
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Spacer(MaterialTheme.spacing.normal)
        Text(stringResource(R.string.screen_categories_empty_title), style = MaterialTheme.typography.titleLarge)
        Spacer(MaterialTheme.spacing.medium)
        Text(
            stringResource(R.string.screen_categories_empty_description),
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(Percent.THREE_QUARTERS.fraction),
        )
        Spacer(MaterialTheme.spacing.large)
        Button(onClick = onAddCategoryClicked) {
            Icon(
                imageVector = MaterialTheme.icons.add,
                contentDescription = stringResource(R.string.common_cd_add_category),
                modifier = Modifier.size(ButtonDefaults.IconSize),
            )
            Spacer(ButtonDefaults.IconSpacing)
            Text(stringResource(R.string.screen_categories_empty_button))
        }
    }
}

@Composable
private fun CategoriesList(
    isLoading: Boolean,
    categories: List<Category>,
    lastMonthTransactions: List<Transaction>,
    onSendIntent: (CategoriesIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    Box(
        contentAlignment = Alignment.Center,
        modifier = modifier,
    ) {
        LazyColumn(
            contentPadding = PaddingValues(
                start = MaterialTheme.spacing.medium,
                end = MaterialTheme.spacing.medium,
                top = MaterialTheme.spacing.medium,
                bottom = MaterialTheme.spacing.giant,
            ),
            verticalArrangement = Arrangement.spacedBy(
                MaterialTheme.spacing.medium,
                Alignment.Top,
            ),
            modifier = Modifier.fillMaxSize(),
        ) {
            items(
                categories,
                key = { category -> category.id },
            ) { category ->
                CategoryItem(
                    category = category,
                    spentAmount = category.calculateSpentAmount(lastMonthTransactions),
                    onClicked = {
                        onSendIntent(CategoriesIntent.NavigateToCreteOrEditCategory(category.id))
                    },
                    deleteEnabled = isLoading.not(),
                    onDeleteClicked = {
                        onSendIntent(CategoriesIntent.DeleteCategory(category))
                    },
                    modifier = Modifier
                        .fillMaxWidth(),
                )
            }
        }

        if (isLoading) {
            CircularProgressIndicator()
        }
    }
}

@Composable
fun CategoriesDialog(
    dialogState: CategoriesDialogState,
    onSendIntent: (CategoriesIntent) -> Unit,
) {
    when (dialogState) {
        CategoriesDialogState.None -> {
            // no-op
        }
        is CategoriesDialogState.CategoryWithDependenciesWarning -> ConfirmationDialog(
            cancelButtonVisible = false,
            onDismissRequest = { onSendIntent(CategoriesIntent.DismissDialog) },
            onConfirm = { onSendIntent(CategoriesIntent.DismissDialog) },
            title = stringResource(R.string.screen_categories_with_dependencies_warning_dialog_title),
            description = stringResource(
                R.string.screen_categories_with_dependencies_warning_dialog_description,
                dialogState.category.name,
            ),
            confirmButtonTitle = stringResource(R.string.common_dialog_button_ok),
            confirmButtonColors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onPrimary,
                containerColor = MaterialTheme.colorScheme.primary,
            ),
        )

        is CategoriesDialogState.DeleteCategoryConfirmation -> ConfirmationDialog(
            onDismissRequest = { onSendIntent(CategoriesIntent.DismissDialog) },
            onConfirm = { onSendIntent(CategoriesIntent.ConfirmCategoryDelete(dialogState.category)) },
            title = stringResource(R.string.screen_categories_delete_confirmation_dialog_title),
            description = stringResource(R.string.screen_categories_delete_confirmation_dialog_description),
            confirmButtonTitle = stringResource(R.string.common_dialog_button_delete),
            confirmButtonColors = ButtonDefaults.buttonColors(
                contentColor = MaterialTheme.colorScheme.onErrorContainer,
                containerColor = MaterialTheme.colorScheme.errorContainer,
            ),
        )
    }
}
