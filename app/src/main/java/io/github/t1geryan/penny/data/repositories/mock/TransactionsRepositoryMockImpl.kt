package io.github.t1geryan.penny.data.repositories.mock

import io.github.t1geryan.domain.models.Amount
import io.github.t1geryan.domain.models.Category
import io.github.t1geryan.domain.models.CategoryId
import io.github.t1geryan.domain.models.Currency
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.domain.repositories.TransactionsRepository
import kotlinx.coroutines.delay
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.MutableStateFlow
import kotlinx.coroutines.flow.map
import kotlinx.datetime.LocalDateTime
import javax.inject.Inject
import kotlin.time.Duration
import kotlin.time.Duration.Companion.milliseconds

class TransactionsRepositoryMockImpl @Inject constructor() : TransactionsRepository {

    private val categoriesFlow = MutableStateFlow(initialCategories())
    private val transactionsFlow = MutableStateFlow(initialTransactions(categoriesFlow.value))

    private var nextTransactionId = transactionsFlow.value.maxOfOrNull { it.id }?.plus(1) ?: 1

    private var nextCategoryId = categoriesFlow.value.maxOfOrNull { it.id }?.plus(1) ?: 1

    override fun observeTransactions(): Flow<List<Transaction>> = transactionsFlow

    override fun observeTransactionsByCategory(categoryId: CategoryId): Flow<List<Transaction>> {
        return transactionsFlow.map { list ->
            list.filter { it.category.id == categoryId }
        }
    }

    override fun observeCategories(): Flow<List<Category>> = categoriesFlow

    override suspend fun createTransaction(transaction: Transaction): TransactionId {
        delay(DEFAULT_DELAY)

        val category = categoriesFlow.value.first { it.id == transaction.category.id }

        val id = nextTransactionId++
        val newTransaction = transaction.copy(
            id = id,
            category = category,
        )

        transactionsFlow.value += newTransaction

        return id
    }

    override suspend fun updateTransaction(transaction: Transaction) {
        delay(DEFAULT_DELAY)

        val category = categoriesFlow.value.first { it.id == transaction.category.id }

        transactionsFlow.value = transactionsFlow.value.map {
            if (it.id == transaction.id) {
                transaction.copy(category = category)
            } else {
                it
            }
        }
    }

    override suspend fun deleteTransactionById(id: TransactionId) {
        delay(DEFAULT_DELAY)

        transactionsFlow.value = transactionsFlow.value.filterNot {
            it.id == id
        }
    }

    override suspend fun createCategory(category: Category): CategoryId {
        delay(DEFAULT_DELAY)

        val id = nextCategoryId++
        val newCategory = category.copy(id = id)

        categoriesFlow.value += newCategory

        return id
    }

    override suspend fun updateCategory(category: Category) {
        delay(DEFAULT_DELAY)

        categoriesFlow.value = categoriesFlow.value.map {
            if (it.id == category.id) category else it
        }

        transactionsFlow.value = transactionsFlow.value.map {
            if (it.category.id == category.id) {
                it.copy(category = category)
            } else {
                it
            }
        }
    }

    override suspend fun deleteCategoryById(id: CategoryId) {
        delay(DEFAULT_DELAY)

        categoriesFlow.value = categoriesFlow.value.filterNot { it.id == id }

        transactionsFlow.value = transactionsFlow.value.filterNot {
            it.category.id == id
        }
    }

    companion object {

        private val DEFAULT_DELAY: Duration = 300.milliseconds

        private fun initialCategories(): List<Category> {

            val currency = Currency.RUSSIAN_RUBLE

            return listOf(
                Category(
                    id = 1,
                    name = "Food",
                    emoji = "🍔",
                    color = 0xFFE57373,
                    limit = Amount(500_000, currency),
                ),
                Category(
                    id = 2,
                    name = "Transport",
                    emoji = "🚌",
                    color = 0xFF64B5F6,
                    limit = null,
                ),
                Category(
                    id = 3,
                    name = "Entertainment",
                    emoji = "🎮",
                    color = 0xFFBA68C8,
                    limit = null,
                ),
                Category(
                    id = 4,
                    name = "Healthcare",
                    emoji = "🏥",
                    color = 0xFFBAC4d4,
                    limit = null,
                ),
                Category(
                    id = 5,
                    name = "Bills",
                    emoji = "💡",
                    color = 0xFFC4BA6A,
                    limit = null,
                ),
                Category(
                    id = 6,
                    name = "Shopping",
                    emoji = "🛍️",
                    color = 0xFFCFA9CB,
                    limit = null,
                ),
            )
        }

        private fun initialTransactions(categories: List<Category>): List<Transaction> {

            val currency = Currency.RUSSIAN_RUBLE

            val food = categories.first { it.id == 1 }
            val transport = categories.first { it.id == 2 }
            val entertainment = categories.first { it.id == 3 }

            return listOf(
                Transaction(
                    id = 1,
                    name = "Burger",
                    amount = Amount(45000, currency),
                    category = food,
                    date = LocalDateTime(2026, 3, 1, 13, 20),
                ),
                Transaction(
                    id = 2,
                    name = "Coffee",
                    amount = Amount(20000, currency),
                    category = food,
                    date = LocalDateTime(2026, 3, 2, 9, 10),
                ),
                Transaction(
                    id = 3,
                    name = "Bus ticket",
                    amount = Amount(6000, currency),
                    category = transport,
                    date = LocalDateTime(2026, 3, 3, 8, 30),
                ),
                Transaction(
                    id = 4,
                    name = "Steam game",
                    amount = Amount(150000, currency),
                    category = entertainment,
                    date = LocalDateTime(2026, 3, 4, 22, 0),
                )
            )
        }
    }
}
