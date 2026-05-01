package io.github.t1geryan.penny.data.database

import androidx.room.Database
import androidx.room.RoomDatabase
import androidx.room.TypeConverters
import io.github.t1geryan.penny.data.database.converters.AmountConverter
import io.github.t1geryan.penny.data.database.converters.CurrencyConverter
import io.github.t1geryan.penny.data.database.converters.LocalDateTimeConverter
import io.github.t1geryan.penny.data.database.dao.CategoriesDao
import io.github.t1geryan.penny.data.database.dao.TransactionsDao
import io.github.t1geryan.penny.data.database.entities.CategoryEntity
import io.github.t1geryan.penny.data.database.entities.TransactionEntity

@Database(
    version = 1,
    entities = [
        TransactionEntity::class,
        CategoryEntity::class,
    ],
)
@TypeConverters(
    AmountConverter::class,
    CurrencyConverter::class,
    LocalDateTimeConverter::class,
)
abstract class PennyDatabase : RoomDatabase() {

    object Tables {
        const val TRANSACTIONS = "transactions"
        const val CATEGORIES = "categories"
    }

    abstract fun getTransactionsDao(): TransactionsDao

    abstract fun getCategoriesDao(): CategoriesDao
}
