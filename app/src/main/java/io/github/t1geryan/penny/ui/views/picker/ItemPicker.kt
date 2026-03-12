package io.github.t1geryan.penny.ui.views.picker

import androidx.compose.animation.core.animateFloatAsState
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.wrapContentHeight
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.BasicAlertDialog
import androidx.compose.material3.Button
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.Surface
import androidx.compose.material3.Text
import androidx.compose.material3.TextButton
import androidx.compose.runtime.Composable
import androidx.compose.runtime.derivedStateOf
import androidx.compose.runtime.getValue
import androidx.compose.runtime.remember
import androidx.compose.runtime.snapshots.SnapshotStateList
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.draw.alpha
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.window.DialogProperties
import io.github.t1geryan.penny.R
import io.github.t1geryan.penny.ui.views.spacing.Spacer
import io.github.t1geryan.theme.cornerRadius
import io.github.t1geryan.theme.icons
import io.github.t1geryan.theme.spacing

typealias ItemContentBlock<T> = @Composable (item: T, isSelected: Boolean, onSelect: () -> Unit) -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T> ItemPicker(
    title: String,
    items: List<T>,
    initiallySelectedItems: List<T>,
    itemContent: ItemContentBlock<T>,
    onDismissRequest: () -> Unit,
    onItemsSelected: (List<T>) -> Unit,
    modifier: Modifier = Modifier,
    key: ((T) -> Unit)? = null,
    confirmButtonTitle: String = stringResource(R.string.common_dialog_button_apply),
    dismissButtonTitle: String = stringResource(R.string.common_dialog_button_cancel),
    properties: DialogProperties = DialogProperties(usePlatformDefaultWidth = false),
    enableWhenNoItemsSelected: Boolean = false,
) {
    val selectedItems = remember {
        SnapshotStateList<T>().also { it.addAll(initiallySelectedItems) }
    }
    val hasSelectedItems by remember {
        derivedStateOf {
            selectedItems.isNotEmpty()
        }
    }
    val clearAllAlpha by animateFloatAsState(if (hasSelectedItems) 1.0f else 0.0f)

    BasicAlertDialog(
        onDismissRequest = onDismissRequest,
        properties = properties,
        modifier = modifier,
    ) {
        Surface(
            color = MaterialTheme.colorScheme.surfaceContainer,
            shape = RoundedCornerShape(MaterialTheme.cornerRadius.large),
            modifier = Modifier.wrapContentHeight(Alignment.Top),
        ) {
            Column(
                modifier = Modifier.padding(MaterialTheme.spacing.normal),
            ) {
                Row(
                    horizontalArrangement = Arrangement.SpaceBetween,
                    verticalAlignment = Alignment.CenterVertically,
                    modifier = Modifier.fillMaxWidth(),
                ) {
                    Text(title, style = MaterialTheme.typography.headlineSmall)
                    TextButton(
                        onClick = { selectedItems.clear() },
                        modifier = Modifier.alpha(clearAllAlpha),
                    ) {
                        Icon(MaterialTheme.icons.close, contentDescription = null)
                        Text(stringResource(R.string.common_dialog_picker_clear_all))
                    }
                }
                Spacer(MaterialTheme.spacing.normal)
                LazyColumn(
                    verticalArrangement = Arrangement.spacedBy(MaterialTheme.spacing.small),
                    modifier = Modifier.weight(1.0f, fill = false),
                ) {
                    items(items, key = key) { item ->
                        itemContent(
                            item,
                            selectedItems.contains(item),
                        ) {
                            val index = selectedItems.indexOf(item)
                            if (index == -1) {
                                selectedItems.add(item)
                            } else {
                                selectedItems.removeAt(index)
                            }
                        }
                    }
                }
                Spacer(MaterialTheme.spacing.normal)
                // Buttons
                Row(modifier = Modifier.fillMaxWidth()) {
                    TextButton(
                        onClick = onDismissRequest,
                        modifier = Modifier.weight(1.0f),
                    ) {
                        Text(dismissButtonTitle)
                    }
                    Spacer(MaterialTheme.spacing.small)
                    Button(
                        enabled = enableWhenNoItemsSelected || hasSelectedItems,
                        onClick = {
                            onItemsSelected(selectedItems)
                            onDismissRequest()
                        },
                        modifier = Modifier.weight(1.0f),
                    ) {
                        val counter = if (hasSelectedItems) {
                            " " + stringResource(R.string.common_counter, selectedItems.size)
                        } else ""
                        Text("$confirmButtonTitle$counter")
                    }
                }
            }
        }
    }
}
