package io.github.t1geryan.penny.ui.contracts

import android.content.Context
import io.github.t1geryan.penny.R
import kotlinx.datetime.format.MonthNames

val Context.monthNames
    get() = MonthNames(resources.getStringArray(R.array.common_short_month_names).toList())
