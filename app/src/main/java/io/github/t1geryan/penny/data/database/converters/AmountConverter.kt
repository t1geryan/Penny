package io.github.t1geryan.penny.data.database.converters

import androidx.room.TypeConverter
import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.Currency

class AmountConverter {

    @TypeConverter
    fun fromAmount(amount: Amount): String = "${amount.value}_${amount.currency.code}"

    @TypeConverter
    fun toAmount(value: String) = value.split("_").let {
        Amount(
            value = it[0].toInt(),
            currency = Currency.getByCode(it[1]),
        )
    }
}
