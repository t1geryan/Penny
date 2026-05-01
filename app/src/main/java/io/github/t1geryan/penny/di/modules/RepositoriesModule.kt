package io.github.t1geryan.penny.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.t1geryan.domain.repositories.TransactionsRepository
import io.github.t1geryan.penny.data.database.dao.CategoriesDao
import io.github.t1geryan.penny.data.database.dao.TransactionsDao
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
    ): TransactionsRepository =
        if (MockedFeaturesFlags.IS_TRANSACTIONS_MOCKED) {
            TransactionsRepositoryMockImpl()
        } else {
            TransactionsRepositoryCoreImpl(categoriesDao, transactionsDao)
        }
}
