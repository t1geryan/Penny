package io.github.t1geryan.domain.usecases

import io.github.t1geryan.coroutines.runSuspendCatching
import io.github.t1geryan.domain.models.Transaction
import io.github.t1geryan.domain.models.TransactionId
import io.github.t1geryan.domain.models.calculateSpentAmount
import io.github.t1geryan.domain.models.thisMonthTransactions
import io.github.t1geryan.domain.repositories.NotificationsRepository
import io.github.t1geryan.domain.repositories.TransactionsRepository
import kotlinx.coroutines.flow.first
import javax.inject.Inject

interface CreateOrUpdateTransactionUseCase {

    suspend operator fun invoke(transaction: Transaction): Result<TransactionId>
}

class CreateOrUpdateTransactionUseCaseImpl @Inject constructor(
    private val transactionsRepository: TransactionsRepository,
    private val notificationsRepository: NotificationsRepository,
) : CreateOrUpdateTransactionUseCase {

    override suspend fun invoke(transaction: Transaction): Result<TransactionId> = runSuspendCatching {
        val categoryTransactionsThisMonthBefore = transactionsRepository
            .observeTransactionsByCategory(transaction.category.id)
            .first()
            .thisMonthTransactions
        val wasOverLimit = transaction.category.limit != null &&
                transaction.category.calculateSpentAmount(categoryTransactionsThisMonthBefore)
                    .isLimitExceed(transaction.category.limit)

        val id = if (transaction.id == 0L) {
            transactionsRepository.createTransaction(transaction)
        } else {
            transactionsRepository.updateTransaction(transaction)
            transaction.id
        }

        if (transaction.category.limit != null && wasOverLimit.not()) {
            val categoryTransactionsThisMonth = transactionsRepository
                .observeTransactionsByCategory(transaction.category.id)
                .first()
                .thisMonthTransactions

            val isOverLimit = transaction.category.calculateSpentAmount(categoryTransactionsThisMonth)
                .isLimitExceed(transaction.category.limit)

            if (isOverLimit) {
                notificationsRepository.sendLimitExceedNotification(transaction.category)
            }
        }

        id
    }
}
