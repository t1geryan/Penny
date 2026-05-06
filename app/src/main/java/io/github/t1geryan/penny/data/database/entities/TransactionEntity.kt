package io.github.t1geryan.penny.data.database.entities

import androidx.room.ColumnInfo
import androidx.room.Entity
import androidx.room.ForeignKey
import androidx.room.Index
import androidx.room.PrimaryKey
import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.penny.data.database.PennyDatabase
import io.github.t1geryan.penny.data.database.entities.TransactionEntity.Columns
import kotlinx.datetime.LocalDateTime

@Entity(
    tableName = PennyDatabase.Tables.TRANSACTIONS,
    foreignKeys = [
        ForeignKey(
            entity = CategoryEntity::class,
            parentColumns = [CategoryEntity.Columns.ID],
            childColumns = [Columns.CATEGORY_ID],
            onDelete = ForeignKey.RESTRICT,
            onUpdate = ForeignKey.NO_ACTION,
        ),
    ],
    indices = [
        Index(value = [Columns.CATEGORY_ID]),
        Index(value = [Columns.UUID], unique = true),
    ],
)
data class TransactionEntity(
    @PrimaryKey(autoGenerate = true) @ColumnInfo(name = Columns.ID, defaultValue = "0") val id: Long,
    @ColumnInfo(name = Columns.UUID) val uuid: String?,
    @ColumnInfo(name = Columns.NAME) val name: String,
    @ColumnInfo(name = Columns.AMOUNT) val amount: Amount,
    @ColumnInfo(name = Columns.CATEGORY_ID) val categoryId: Long,
    @ColumnInfo(name = Columns.DATE) val dateTime: LocalDateTime,
    @ColumnInfo(name = Columns.UPDATED_AT) val updatedAt: LocalDateTime,
) {

    object Columns {
        const val ID = "id"
        const val UUID = "uuid"
        const val NAME = "name"
        const val AMOUNT = "amount"
        const val CATEGORY_ID = "category_id"
        const val DATE = "date"
        const val UPDATED_AT = "updated_at"
    }
}
