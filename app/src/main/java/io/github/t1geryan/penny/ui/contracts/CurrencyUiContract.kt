package io.github.t1geryan.penny.ui.contracts

import androidx.compose.runtime.Composable
import androidx.compose.runtime.ReadOnlyComposable
import androidx.compose.ui.res.stringResource
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.penny.R

val Currency.humanReadableName: String
    @Composable
    @ReadOnlyComposable
    get() = when (this) {
        Currency.US_DOLLAR -> stringResource(R.string.currency_us_dollar)
        Currency.RUSSIAN_RUBLE -> stringResource(R.string.currency_russian_ruble)
    }
