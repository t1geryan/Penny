package io.github.t1geryan.penny.ui.contracts

import android.content.Context
import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.domain.models.Currency.RUSSIAN_RUBLE
import io.github.t1geryan.domain.models.Currency.US_DOLLAR

sealed interface QuickAmount {

    val amount: Amount

    fun updateEntered(value: Float): Amount

    fun format(context: Context): String

    data class PlusAmount(override val amount: Amount) : QuickAmount {
        constructor(value: Float, currency: Currency) : this(Amount(value, currency))

        override fun format(context: Context): String = "+${amount.format()}"

        override fun updateEntered(value: Float): Amount = Amount(value + amount.valueInCurrency, amount.currency)
    }

    data class SetAmount(override val amount: Amount) : QuickAmount {
        constructor(value: Float, currency: Currency) : this(Amount(value, currency))

        override fun format(context: Context): String = amount.format()

        override fun updateEntered(value: Float): Amount = Amount(amount.value, amount.currency)
    }
}

@Suppress("MagicNumber")
val Currency.quickAmounts: List<QuickAmount>
    get() = when (this) {
        US_DOLLAR -> listOf(
            QuickAmount.PlusAmount(1f, this),
            QuickAmount.PlusAmount(5f, this),
            QuickAmount.PlusAmount(10f, this),
            QuickAmount.SetAmount(10f, this),
            QuickAmount.SetAmount(20f, this),
            QuickAmount.SetAmount(50f, this),
            QuickAmount.SetAmount(100f, this),
        )

        RUSSIAN_RUBLE -> listOf(
            QuickAmount.PlusAmount(100f, this),
            QuickAmount.PlusAmount(500f, this),
            QuickAmount.PlusAmount(1000f, this),
            QuickAmount.SetAmount(100f, this),
            QuickAmount.SetAmount(500f, this),
            QuickAmount.SetAmount(1000f, this),
            QuickAmount.SetAmount(5000f, this),
        )
    }
