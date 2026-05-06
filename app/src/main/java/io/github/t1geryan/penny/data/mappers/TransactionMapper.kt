package io.github.t1geryan.penny.data.mappers

import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.penny.data.database.entities.TransactionEntity
import io.github.t1geryan.penny.data.network.dto.TransactionDto
import kotlinx.datetime.LocalDateTime

object TransactionMapper {

    fun fromEntityToModel(transactionEntity: TransactionEntity, category: Category) = Transaction(
        id = transactionEntity.id,
        uuid = transactionEntity.uuid,
        name = transactionEntity.name,
        amount = transactionEntity.amount,
        category = category,
        date = transactionEntity.dateTime,
        updatedAt = transactionEntity.updatedAt,
    )

    fun fromModelToEntity(transaction: Transaction) = TransactionEntity(
        id = transaction.id,
        uuid = transaction.uuid,
        name = transaction.name,
        amount = transaction.amount,
        categoryId = transaction.category.id,
        dateTime = transaction.date,
        updatedAt = transaction.updatedAt,
    )

    fun fromDtoToEntity(transactionDto: TransactionDto, id: TransactionId, categoryId: CategoryId) = TransactionEntity(
        id = id,
        uuid = transactionDto.id,
        name = transactionDto.title,
        amount = Amount(transactionDto.amount.toFloat(), Currency.getByCode(transactionDto.currency)),
        categoryId = categoryId,
        dateTime = LocalDateTime.parse(transactionDto.dateIso, LocalDateTime.Formats.ISO),
        updatedAt = LocalDateTime.parse(transactionDto.updatedAtIso, LocalDateTime.Formats.ISO),
    )
}
