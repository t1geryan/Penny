package io.github.t1geryan.penny.data.database.converters

import androidx.room.TypeConverter
import io.github.t1geryan.domain.models.Currency

class CurrencyConverter {

    @TypeConverter
    fun fromCurrency(currency: Currency) = currency.code

    @TypeConverter
    fun toCurrency(code: String) = Currency.getByCode(code)
}
