package io.github.t1geryan.penny.ui.contracts

import android.content.Context
import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.Transaction

fun Transaction.formatDate(context: Context): String = date.format(context)

fun Amount.format(): String {
    val formattedValue = formatNoCurrency()
    return "${currency.symbol}$formattedValue"
}

fun Amount.formatNoCurrency(): String = OutputFormats.cost().format(valueInCurrency)

fun Amount.formatRaw(): String = OutputFormats.float().format(valueInCurrency)
