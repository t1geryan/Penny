package io.github.t1geryan.penny.di.modules

import android.content.Context
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.t1geryan.domain.repositories.NotificationsRepository
import io.github.t1geryan.domain.repositories.TransactionsRepository
import io.github.t1geryan.penny.data.database.dao.CategoriesDao
import io.github.t1geryan.penny.data.database.dao.TransactionsDao
import io.github.t1geryan.penny.data.network.api.ExpenseApi
import io.github.t1geryan.penny.data.notifications.PennyNotificationsManager
import io.github.t1geryan.penny.data.repositories.core.NotificationsRepositoryImpl
import io.github.t1geryan.penny.data.repositories.core.TransactionsRepositoryCoreImpl
import io.github.t1geryan.penny.data.repositories.mock.TransactionsRepositoryMockImpl
import io.github.t1geryan.penny.di.MockedFeaturesFlags
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun provideTransactionsRepository(
        categoriesDao: CategoriesDao,
        transactionsDao: TransactionsDao,
        expenseApi: ExpenseApi,
    ): TransactionsRepository =
        if (MockedFeaturesFlags.IS_TRANSACTIONS_MOCKED) {
            TransactionsRepositoryMockImpl()
        } else {
            TransactionsRepositoryCoreImpl(categoriesDao, transactionsDao, expenseApi)
        }

    @Provides
    @Singleton
    fun provideNotificationsRepository(
        @ApplicationContext context: Context,
        pennyNotificationsManager: PennyNotificationsManager,
    ): NotificationsRepository = NotificationsRepositoryImpl(context, pennyNotificationsManager)
}
