package io.github.t1geryan.penny.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.PrimaryKey
import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.penny.data.database.PennyDatabase

@Entity(
    tableName = PennyDatabase.Tables.CATEGORIES,
)
data class CategoryEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = Columns.ID, defaultValue = "0") val id: Long,
    @ColumnInfo(name = Columns.NAME) val name: String,
    @ColumnInfo(name = Columns.EMOJI) val emoji: String,
    @ColumnInfo(name = Columns.COLOR) val color: Long,
    @ColumnInfo(name = Columns.LIMIT) val limit: Amount?,
    @ColumnInfo(name = Columns.CURRENCY) val currency: Currency,
) {

    object Columns {
        const val ID = "id"
        const val NAME = "name"
        const val EMOJI = "emoji"
        const val COLOR = "color"
        const val LIMIT = "limit"
        const val CURRENCY = "currency"
    }
}
