package io.github.t1geryan.domain.repositories

import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.domain.models.TransactionId
import kotlinx.coroutines.flow.Flow

interface TransactionsRepository {

    fun observeTransactions(): Flow<List<Transaction>>

    fun observeTransactionById(id: TransactionId): Flow<Transaction?>

    fun observeTransactionsByCategory(categoryId: CategoryId): Flow<List<Transaction>>

    suspend fun createTransaction(transaction: Transaction): TransactionId

    suspend fun updateTransaction(transaction: Transaction)

    suspend fun deleteTransaction(id: TransactionId)
}
