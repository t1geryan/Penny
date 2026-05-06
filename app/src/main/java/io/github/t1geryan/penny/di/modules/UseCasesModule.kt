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
import io.github.t1geryan.domain.usecases.SyncUseCase
import io.github.t1geryan.domain.usecases.SyncUseCaseImpl
import io.github.t1geryan.domain.usecases.ValidateAmountUseCase
import io.github.t1geryan.domain.usecases.ValidateAmountUseCaseImpl

@Module
@InstallIn(SingletonComponent::class)
abstract class UseCasesModule {

    @Binds
    abstract fun bindCreateOrUpdateCategoryUseCase(impl: CreateOrUpdateCategoryUseCaseImpl): CreateOrUpdateCategoryUseCase

    @Binds
    abstract fun bindCreateOrUpdateTransactionUseCase(impl: CreateOrUpdateTransactionUseCaseImpl): CreateOrUpdateTransactionUseCase

    @Binds
    abstract fun bindDeleteCategoryByIdUseCase(impl: DeleteCategoryByIdUseCaseImpl): DeleteCategoryByIdUseCase

    @Binds
    abstract fun bindDeleteTransactionByIdUseCase(impl: DeleteTransactionByIdUseCaseImpl): DeleteTransactionByIdUseCase

    @Binds
    abstract fun bindObserveCategoriesUseCase(impl: ObserveCategoriesUseCaseImpl): ObserveCategoriesUseCase

    @Binds
    abstract fun bindObserveCategoryByIdUseCase(impl: ObserveCategoryByIdUseCaseImpl): ObserveCategoryByIdUseCase

    @Binds
    abstract fun binObserveTransactionsUseCase(impl: ObserveTransactionsUseCaseImpl): ObserveTransactionsUseCase

    @Binds
    abstract fun bindObserveTransactionByIdUseCase(impl: ObserveTransactionByIdUseCaseImpl): ObserveTransactionByIdUseCase

    @Binds
    abstract fun bindValidateAmountUseCase(impl: ValidateAmountUseCaseImpl): ValidateAmountUseCase

    @Binds
    abstract fun bindSyncUseCase(impl: SyncUseCaseImpl): SyncUseCase
}
