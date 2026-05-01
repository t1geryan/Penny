package io.github.t1geryan.penny.data.database.tuples

import androidx.room.Embedded
import androidx.room.Relation
import io.github.t1geryan.penny.data.database.entities.CategoryEntity
import io.github.t1geryan.penny.data.database.entities.TransactionEntity

data class CategoryWithTransactions(
    @Embedded val categoryEntity: CategoryEntity,
    @Relation(
        parentColumn = CategoryEntity.Columns.ID,
        entityColumn = TransactionEntity.Columns.CATEGORY_ID,
    )
    val transactionEntities: List<TransactionEntity>,
)
