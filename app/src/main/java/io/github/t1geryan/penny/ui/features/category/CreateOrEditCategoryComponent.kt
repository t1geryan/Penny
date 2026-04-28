package io.github.t1geryan.penny.ui.features.category

import androidx.compose.runtime.Composable
import androidx.compose.runtime.LaunchedEffect
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.t1geryan.penny.R
import io.github.t1geryan.penny.ui.views.core.ComponentWithTopBar
import io.github.t1geryan.penny.ui.views.core.DefaultBackButton
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

    }

    LaunchedEffect(Unit) {
        eventsFlow.collectLatest { event ->
            when (event) {
                CreateOrEditCategoryEvent.NavigateUp -> onNavigateUp()
            }
        }
    }
}
