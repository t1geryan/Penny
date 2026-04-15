package io.github.t1geryan.penny.ui.views.picker

import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.lazy.LazyColumn
import androidx.compose.foundation.lazy.items
import androidx.compose.material3.ExperimentalMaterial3Api
import androidx.compose.material3.HorizontalDivider
import androidx.compose.material3.Icon
import androidx.compose.material3.IconButton
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.ModalBottomSheet
import androidx.compose.material3.ModalBottomSheetProperties
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.res.stringResource
import io.github.t1geryan.penny.R
import io.github.t1geryan.penny.ui.views.spacing.Expanded
import io.github.t1geryan.penny.ui.views.spacing.Spacer
import io.github.t1geryan.theme.icons
import io.github.t1geryan.theme.spacing

typealias BottomSheetPickerItemContentBlock<T> = @Composable (item: T, isSelected: Boolean) -> Unit

@OptIn(ExperimentalMaterial3Api::class)
@Composable
fun <T : Any> BottomSheetSingleItemPicker(
    onDismissRequest: () -> Unit,
    items: List<T>,
    selectedItem: T?,
    title: String,
    modifier: Modifier = Modifier,
    key: ((T) -> Any)? = null,
    properties: ModalBottomSheetProperties = ModalBottomSheetProperties(),
    itemContent: BottomSheetPickerItemContentBlock<T>,
) {
    ModalBottomSheet(
        properties = properties,
        onDismissRequest = onDismissRequest,
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            modifier = Modifier
                .fillMaxWidth(),
        ) {
            Spacer(MaterialTheme.spacing.small)
            Text(title, style = MaterialTheme.typography.headlineSmall)
            Expanded()
            IconButton(onClick = onDismissRequest) {
                Icon(
                    MaterialTheme.icons.close,
                    contentDescription = stringResource(R.string.common_cd_close),
                )
            }
            Spacer(MaterialTheme.spacing.small)
        }
        HorizontalDivider()
        LazyColumn(modifier = Modifier.fillMaxWidth()) {
            items(items, key = key) { item ->
                itemContent(
                    item,
                    selectedItem == item,
                )
            }
        }
    }
}
