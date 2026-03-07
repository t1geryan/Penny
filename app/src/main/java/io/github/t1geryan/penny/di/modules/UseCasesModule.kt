package io.github.t1geryan.penny.di.modules

import dagger.Binds
import dagger.Module
import dagger.hilt.InstallIn
import dagger.hilt.components.SingletonComponent
import io.github.t1geryan.domain.usecases.CreateOrUpdateCategoryUseCase
import io.github.t1geryan.domain.usecases.CreateOrUpdateCategoryUseCaseImpl
import io.github.t1geryan.domain.usecases.CreateOrUpdateTransactionUseCase
import io.github.t1geryan.domain.usecases.CreateOrUpdateTransactionUseCaseImpl
import io.github.t1geryan.domain.usecases.DeleteCategoryByIdUseCase
import io.github.t1geryan.domain.usecases.DeleteCategoryByIdUseCaseImpl
import io.github.t1geryan.domain.usecases.DeleteTransactionByIdUseCase
import io.github.t1geryan.domain.usecases.DeleteTransactionByIdUseCaseImpl
import io.github.t1geryan.domain.usecases.ObserveCategoriesUseCase
import io.github.t1geryan.domain.usecases.ObserveCategoriesUseCaseImpl
import io.github.t1geryan.domain.usecases.ObserveCategoryByIdUseCase
import io.github.t1geryan.domain.usecases.ObserveCategoryByIdUseCaseImpl
import io.github.t1geryan.domain.usecases.ObserveTransactionByIdUseCase
import io.github.t1geryan.domain.usecases.ObserveTransactionByIdUseCaseImpl
import io.github.t1geryan.domain.usecases.ObserveTransactionsUseCase
import io.github.t1geryan.domain.usecases.ObserveTransactionsUseCaseImpl
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {

    @Binds
    @Singleton
    abstract fun bindCreateOrUpdateCategoryUseCase(impl: CreateOrUpdateCategoryUseCaseImpl): CreateOrUpdateCategoryUseCase

    @Binds
    @Singleton
    abstract fun bindCreateOrUpdateTransactionUseCase(impl: CreateOrUpdateTransactionUseCaseImpl): CreateOrUpdateTransactionUseCase

    @Binds
    @Singleton
    abstract fun bindDeleteCategoryByIdUseCase(impl: DeleteCategoryByIdUseCaseImpl): DeleteCategoryByIdUseCase

    @Binds
    @Singleton
    abstract fun bindDeleteTransactionByIdUseCase(impl: DeleteTransactionByIdUseCaseImpl): DeleteTransactionByIdUseCase

    @Binds
    @Singleton
    abstract fun bindObserveCategoriesUseCase(impl: ObserveCategoriesUseCaseImpl): ObserveCategoriesUseCase

    @Binds
    @Singleton
    abstract fun bindObserveCategoryByIdUseCase(impl: ObserveCategoryByIdUseCaseImpl): ObserveCategoryByIdUseCase

    @Binds
    @Singleton
    abstract fun binObserveTransactionsUseCase(impl: ObserveTransactionsUseCaseImpl): ObserveTransactionsUseCase

    @Binds
    @Singleton
    abstract fun bindObserveTransactionByIdUseCase(impl: ObserveTransactionByIdUseCaseImpl): ObserveTransactionByIdUseCase
}
