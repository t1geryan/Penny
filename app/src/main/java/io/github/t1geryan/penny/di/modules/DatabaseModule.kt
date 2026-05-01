package io.github.t1geryan.penny.di.modules

import android.content.Context
import androidx.room.Room
import dagger.Module
import dagger.Provides
import dagger.hilt.InstallIn
import dagger.hilt.android.qualifiers.ApplicationContext
import dagger.hilt.components.SingletonComponent
import io.github.t1geryan.penny.data.database.PennyDatabase
import javax.inject.Singleton

@Module
@InstallIn(SingletonComponent::class)
object DatabaseModule {

    private const val DATABASE_NAME = "penny_database.db"

    @Provides
    @Singleton
    fun providePennyDatabase(@ApplicationContext context: Context) = Room
        .databaseBuilder(context, PennyDatabase::class.java, DATABASE_NAME)
        .build()

    @Provides
    @Singleton
    fun provideTransactionsDao(pennyDatabase: PennyDatabase) = pennyDatabase.getTransactionsDao()

    @Provides
    @Singleton
    fun provideCategoriesDao(pennyDatabase: PennyDatabase) = pennyDatabase.getCategoriesDao()
}
