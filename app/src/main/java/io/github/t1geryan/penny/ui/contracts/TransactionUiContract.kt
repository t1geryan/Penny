package io.github.t1geryan.penny.ui.contracts

import android.content.Context
import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.Transaction
import kotlinx.datetime.TimeZone
import kotlinx.datetime.format
import kotlinx.datetime.toLocalDateTime
import java.util.Locale
import kotlin.time.Clock

fun Transaction.formatDate(context: Context): String {
    val currentYear = Clock.System.now().toLocalDateTime(TimeZone.currentSystemDefault()).year
    return if (date.year == currentYear) {
        date.format(OutputFormats.shortDate(context.monthNames))
    } else {
        date.format(OutputFormats.fullDate(context.monthNames))
    }
}

fun Amount.format(): String {
    val formattedValue = OutputFormats.cost.format(
        Locale.getDefault(),
        value * currency.subUnitMultiplier,
    )
    return "${currency.symbol}$formattedValue"
}
