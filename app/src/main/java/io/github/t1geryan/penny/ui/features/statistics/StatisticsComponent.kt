package io.github.t1geryan.penny.ui.features.statistics

import androidx.compose.animation.core.Spring
import androidx.compose.animation.core.spring
import androidx.compose.foundation.background
import androidx.compose.foundation.layout.Arrangement
import androidx.compose.foundation.layout.Column
import androidx.compose.foundation.layout.ColumnScope
import androidx.compose.foundation.layout.PaddingValues
import androidx.compose.foundation.layout.Row
import androidx.compose.foundation.layout.fillMaxHeight
import androidx.compose.foundation.layout.fillMaxSize
import androidx.compose.foundation.layout.fillMaxWidth
import androidx.compose.foundation.layout.padding
import androidx.compose.foundation.layout.size
import androidx.compose.foundation.shape.CircleShape
import androidx.compose.foundation.shape.RoundedCornerShape
import androidx.compose.material3.FilledTonalButton
import androidx.compose.material3.Icon
import androidx.compose.material3.MaterialTheme
import androidx.compose.material3.OutlinedCard
import androidx.compose.material3.Text
import androidx.compose.runtime.Composable
import androidx.compose.ui.Alignment
import androidx.compose.ui.Modifier
import androidx.compose.ui.graphics.SolidColor
import androidx.compose.ui.graphics.vector.ImageVector
import androidx.compose.ui.platform.LocalContext
import androidx.compose.ui.res.stringResource
import androidx.compose.ui.text.style.TextAlign
import androidx.compose.ui.unit.dp
import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.calculateSpentAmount
import io.github.t1geryan.models.Percent
import io.github.t1geryan.penny.R
import io.github.t1geryan.penny.ui.contracts.contentColor
import io.github.t1geryan.penny.ui.contracts.format
import io.github.t1geryan.penny.ui.views.core.ComponentWithTopBar
import io.github.t1geryan.penny.ui.views.picker.PennyDateRangePicker
import io.github.t1geryan.penny.ui.views.spacing.Spacer
import io.github.t1geryan.theme.cornerRadius
import io.github.t1geryan.theme.icons
import io.github.t1geryan.theme.spacing
import ir.ehsannarmani.compose_charts.ColumnChart
import ir.ehsannarmani.compose_charts.models.BarProperties
import ir.ehsannarmani.compose_charts.models.Bars
import ir.ehsannarmani.compose_charts.models.GridProperties
import ir.ehsannarmani.compose_charts.models.HorizontalIndicatorProperties
import ir.ehsannarmani.compose_charts.models.IndicatorPosition
import ir.ehsannarmani.compose_charts.models.LabelHelperProperties
import ir.ehsannarmani.compose_charts.models.LabelProperties
import ir.ehsannarmani.compose_charts.models.PopupProperties
import kotlin.math.roundToInt

@Composable
fun StatisticsComponent(
    state: StatisticsState,
    onSendIntent: (StatisticsIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    ComponentWithTopBar(
        title = stringResource(R.string.screen_statistics_title),
        modifier = modifier,
    ) {
        Column(
            modifier = Modifier
                .fillMaxSize()
                .padding(MaterialTheme.spacing.medium),
        ) {
            when (state.emptyState) {
                StatisticsEmptyState.NoCategories, StatisticsEmptyState.NoTransactions -> EmptyState(
                    state.emptyState,
                    Modifier.fillMaxSize(),
                )
                StatisticsEmptyState.NoTransactionsInRange, StatisticsEmptyState.NotEmpty -> Content(
                    state = state,
                    onSendIntent = onSendIntent,
                    modifier = Modifier.fillMaxWidth(),
                )
            }
        }
    }

    StatisticsDialog(dialogState = state.dialogState, onSendIntent = onSendIntent)
}

@Composable
private fun ColumnScope.Content(
    state: StatisticsState,
    onSendIntent: (StatisticsIntent) -> Unit,
    modifier: Modifier = Modifier,
) {
    FilledTonalButton(
        onClick = { onSendIntent(StatisticsIntent.PickRange) },
        shape = RoundedCornerShape(MaterialTheme.cornerRadius.large),
        contentPadding = PaddingValues(MaterialTheme.spacing.medium),
        modifier = modifier,
    ) {
        Row(
            verticalAlignment = Alignment.CenterVertically,
            horizontalArrangement = Arrangement.Start,
            modifier = Modifier.fillMaxWidth(),
        ) {
            Icon(
                MaterialTheme.icons.calendar,
                contentDescription = null,
            )
            Spacer(MaterialTheme.spacing.small)
            Text(
                state.dateRange.format(LocalContext.current),
            )
        }
    }
    Spacer(MaterialTheme.spacing.medium)
    if (state.emptyState is StatisticsEmptyState.NotEmpty) {
        Chart(
            state = state,
            modifier = Modifier
                .fillMaxWidth()
                .fillMaxHeight(Percent.HALF.fraction)
                .padding(MaterialTheme.spacing.large),
        )
    } else if (state.emptyState is StatisticsEmptyState.NoTransactionsInRange) {
        EmptyState(
            emptyState = state.emptyState,
            modifier = Modifier.fillMaxSize(),
        )
    }
}

@Composable
fun Chart(
    state: StatisticsState,
    modifier: Modifier = Modifier,
) {
    val data = state.categories.map { category ->
        val spent = category.calculateSpentAmount(state.filteredTransactions)
        Bars(
            category.emoji,
            values = listOf(
                Bars.Data(
                    id = category.id.toInt(),
                    value = spent.valueInCurrency.toDouble(),
                    color = SolidColor(category.contentColor),
                ),
            ),
        )
    }
    OutlinedCard {
        ColumnChart(
            modifier = modifier,
            data = data,
            gridProperties = GridProperties(
                enabled = false,
            ),
            labelProperties = LabelProperties(
                enabled = true,
                padding = MaterialTheme.spacing.tiny,
                textStyle = MaterialTheme.typography.labelSmall.copy(textAlign = TextAlign.Center),
            ),
            indicatorProperties = HorizontalIndicatorProperties(
                enabled = true,
                textStyle = MaterialTheme.typography.labelMedium.copy(
                    color = MaterialTheme.colorScheme.onBackground,
                ),
                contentBuilder = { value ->
                    value.roundToInt().toString()
                },
                position = IndicatorPosition.Horizontal.Start,
                padding = 0.dp,
            ),
            popupProperties = PopupProperties(
                containerColor = MaterialTheme.colorScheme.surfaceContainerLow,
                contentBuilder = { popup ->
                    val id = data[popup.dataIndex].values[popup.valueIndex].id
                    val category = state.categories.first { it.id.toInt() == id }
                    "${category.name}\n${Amount(popup.value.toFloat(), category.currency).format()}"
                },
                textStyle = MaterialTheme.typography.labelSmall.copy(
                    color = MaterialTheme.colorScheme.onSurface,
                ),
            ),
            labelHelperProperties = LabelHelperProperties(
                enabled = false,
            ),
            barProperties = BarProperties(
                cornerRadius = Bars.Data.Radius.Rectangle(
                    topRight = MaterialTheme.cornerRadius.medium,
                    topLeft = MaterialTheme.cornerRadius.medium,
                ),
                thickness = MaterialTheme.spacing.large,
            ),
            animationSpec = spring(
                dampingRatio = Spring.DampingRatioMediumBouncy,
                stiffness = Spring.StiffnessLow,
            ),
            animationDelay = 0,
        )
    }
}

@Composable
private fun EmptyState(
    emptyState: StatisticsEmptyState,
    modifier: Modifier = Modifier,
) {
    when (emptyState) {
        StatisticsEmptyState.NoCategories -> EmptyContent(
            icon = MaterialTheme.icons.add,
            title = stringResource(R.string.screen_statistics_empty_state_no_categories_title),
            description = stringResource(R.string.screen_statistics_empty_state_no_categories_description),
            modifier = modifier,
        )
        StatisticsEmptyState.NoTransactions -> EmptyContent(
            icon = MaterialTheme.icons.add,
            title = stringResource(R.string.screen_statistics_empty_state_no_transactions_title),
            description = stringResource(R.string.screen_statistics_empty_state_no_transactions_description),
            modifier = modifier,
        )
        StatisticsEmptyState.NoTransactionsInRange -> EmptyContent(
            icon = MaterialTheme.icons.calendar,
            title = stringResource(R.string.screen_statistics_empty_state_no_transactions_in_range_title),
            description = stringResource(R.string.screen_statistics_empty_state_no_transactions_in_range_description),
            modifier = modifier,
        )
        StatisticsEmptyState.NotEmpty -> {}
    }
}

@Composable
private fun EmptyContent(
    icon: ImageVector,
    title: String,
    description: String,
    modifier: Modifier,
) {
    Column(
        verticalArrangement = Arrangement.Center,
        horizontalAlignment = Alignment.CenterHorizontally,
        modifier = modifier,
    ) {
        Icon(
            icon,
            contentDescription = null,
            modifier = Modifier
                .size(64.dp)
                .background(MaterialTheme.colorScheme.primaryContainer, CircleShape)
                .padding(MaterialTheme.spacing.normal),
            tint = MaterialTheme.colorScheme.onPrimaryContainer,
        )
        Spacer(MaterialTheme.spacing.normal)
        Text(title, style = MaterialTheme.typography.titleLarge, textAlign = TextAlign.Center)
        Spacer(MaterialTheme.spacing.medium)
        Text(
            description,
            style = MaterialTheme.typography.bodyLarge,
            textAlign = TextAlign.Center,
            modifier = Modifier.fillMaxWidth(Percent.THREE_QUARTERS.fraction),
        )
    }
}

@Composable
private fun StatisticsDialog(dialogState: StatisticsDialogState, onSendIntent: (StatisticsIntent) -> Unit) {
    when (dialogState) {
        StatisticsDialogState.None -> {}
        StatisticsDialogState.RangePickerDialog -> PennyDateRangePicker(
            onDismissRequest = { onSendIntent(StatisticsIntent.DismissDialog) },
            onRangeSelected = { onSendIntent(StatisticsIntent.SetRange(it)) },
            modifier = Modifier.fillMaxHeight(fraction = Percent.HALF.fraction),
        )
    }
}
