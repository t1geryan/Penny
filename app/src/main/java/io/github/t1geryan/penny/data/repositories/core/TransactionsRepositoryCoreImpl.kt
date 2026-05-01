package io.github.t1geryan.penny.data.repositories.core

import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.domain.repositories.TransactionsRepository
import io.github.t1geryan.penny.data.database.dao.CategoriesDao
import io.github.t1geryan.penny.data.database.dao.TransactionsDao
import io.github.t1geryan.penny.data.mappers.CategoryMapper
import io.github.t1geryan.penny.data.mappers.TransactionMapper
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import javax.inject.Inject

class TransactionsRepositoryCoreImpl @Inject constructor(
    private val categoriesDao: CategoriesDao,
    private val transactionsDao: TransactionsDao,
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
