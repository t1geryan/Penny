package io.github.t1geryan.penny.data.repositories.core

import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.domain.repositories.TransactionsRepository
import io.github.t1geryan.penny.data.database.dao.CategoriesDao
import io.github.t1geryan.penny.data.database.dao.TransactionsDao
import io.github.t1geryan.penny.data.mappers.CategoryMapper
import io.github.t1geryan.penny.data.mappers.TransactionMapper
import io.github.t1geryan.penny.data.network.api.ExpenseApi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject

class TransactionsRepositoryCoreImpl @Inject constructor(
    private val categoriesDao: CategoriesDao,
    private val transactionsDao: TransactionsDao,
    private val expenseApi: ExpenseApi,
) : TransactionsRepository {
    override fun observeTransactions(): Flow<List<Transaction>> =
        categoriesDao.getAllWithTransactions().map { categoryWithTransactionsList ->
            categoryWithTransactionsList.flatMap { categoryWithTransactions ->
                val category = CategoryMapper.fromEntityToModel(categoryWithTransactions.categoryEntity)
                categoryWithTransactions.transactionEntities.map { transactionEntity ->
                    TransactionMapper.fromEntityToModel(transactionEntity, category)
                }
            }
        }

    override fun observeTransactionsByCategory(categoryId: CategoryId): Flow<List<Transaction>> =
        categoriesDao.getByIdWithTransactions(categoryId).map { categoryWithTransactions ->
            val category = CategoryMapper.fromEntityToModel(categoryWithTransactions.categoryEntity)
            categoryWithTransactions.transactionEntities.map { transactionEntity ->
                TransactionMapper.fromEntityToModel(transactionEntity, category)
            }
        }

    override fun observeCategories(): Flow<List<Category>> = categoriesDao.getAll().map { categoryEntities ->
        categoryEntities.map { categoryEntity ->
            CategoryMapper.fromEntityToModel(categoryEntity)
        }
    }

    override fun observeCategoryByName(name: String): Flow<Category?> =
        categoriesDao.getByName(name).map { categoryEntity ->
            categoryEntity?.let { CategoryMapper.fromEntityToModel(it) }
        }

    override suspend fun syncTransactions() {
        val response = expenseApi.getTransactions().transactions
        val existingTransactions = observeTransactions().first()

        response.forEach { transactionDto ->
            val existingTransaction = existingTransactions.firstOrNull { it.uuid == transactionDto.id }
            if (existingTransaction != null && existingTransaction.updatedAt >= LocalDateTime.parse(
                    transactionDto.updatedAtIso,
                    LocalDateTime.Formats.ISO,
                )
            ) {
                // No need to update
                return@forEach
            }

            val localId = existingTransaction?.id ?: 0L
            val categoryId = observeCategoryByName(transactionDto.category).first()?.id ?: Category.DEFAULT.copy(
                name = transactionDto.category,
                currency = Currency.getByCode(transactionDto.currency),
            ).let {
                createCategory(it)
            }

            val transactionEntity = TransactionMapper.fromDtoToEntity(transactionDto, localId, categoryId)

            transactionsDao.upsertTransaction(transactionEntity)
        }
    }

    override suspend fun createTransaction(transaction: Transaction): TransactionId =
        transactionsDao.upsertTransaction(TransactionMapper.fromModelToEntity(transaction))

    override suspend fun updateTransaction(transaction: Transaction) {
        transactionsDao.upsertTransaction(TransactionMapper.fromModelToEntity(transaction))
    }

    override suspend fun deleteTransactionById(id: TransactionId) = transactionsDao.deleteById(id)

    override suspend fun createCategory(category: Category): CategoryId =
        categoriesDao.upsertCategory(CategoryMapper.fromModelToEntity(category))

    override suspend fun updateCategory(category: Category) {
        categoriesDao.upsertCategory(CategoryMapper.fromModelToEntity(category))
    }

    override suspend fun deleteCategoryById(id: CategoryId) {
        categoriesDao.deleteById(id)
    }
}
