package io.github.t1geryan.penny.ui.contracts

import android.content.Context
import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.Transaction

fun Transaction.formatDate(context: Context): String = date.format(context)

fun Amount.format(): String {
    val formattedValue = OutputFormats.cost().format(valueInCurrency)
    return "${currency.symbol}$formattedValue"
}
