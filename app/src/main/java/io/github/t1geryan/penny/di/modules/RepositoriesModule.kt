package io.github.t1geryan.penny.di.modules

import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.t1geryan.domain.repositories.TransactionsRepository
import io.github.t1geryan.penny.data.repositories.mock.TransactionsRepositoryMockImpl
import io.github.t1geryan.penny.di.MockedFeaturesFlags
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object RepositoriesModule {

    @Provides
    @Singleton
    fun provideTransactionsRepository(): TransactionsRepository =
        if (MockedFeaturesFlags.IS_TRANSACTIONS_MOCKED) {
            TransactionsRepositoryMockImpl()
        } else {
            TODO("Implement core transactions repo")
        }
}
