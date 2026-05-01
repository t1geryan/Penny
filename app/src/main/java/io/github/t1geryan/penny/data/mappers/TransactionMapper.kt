package io.github.t1geryan.penny.data.mappers

import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.penny.data.database.entities.TransactionEntity

object TransactionMapper {

    fun fromEntityToModel(transactionEntity: TransactionEntity, category: Category) = Transaction(
        id = transactionEntity.id,
        name = transactionEntity.name,
        amount = transactionEntity.amount,
        category = category,
        date = transactionEntity.dateTime,
    )

    fun fromModelToEntity(transaction: Transaction) = TransactionEntity(
        id = transaction.id,
        name = transaction.name,
        amount = transaction.amount,
        categoryId = transaction.category.id,
        dateTime = transaction.date,
    )
}
