package io.github.t1geryan.domain.repositories

import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.domain.models.TransactionId
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {

    fun observeTransactions(): Flow<List<Transaction>>

    fun observeTransactionsByCategory(categoryId: CategoryId): Flow<List<Transaction>>

    fun observeCategories(): Flow<List<Category>>

    suspend fun createTransaction(transaction: Transaction): TransactionId

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransactionById(id: TransactionId)

    suspend fun createCategory(category: Category): CategoryId

    suspend fun updateCategory(category: Category)

    suspend fun deleteCategoryById(id: CategoryId)
}
