package io.github.t1geryan.penny.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Transaction
import androidx.room.Upsert
import io.github.t1geryan.penny.data.database.PennyDatabase
import io.github.t1geryan.penny.data.database.entities.CategoryEntity
import io.github.t1geryan.penny.data.database.tuples.CategoryWithTransactions
import kotlinx.coroutines.flow.Flow

@Dao
interface CategoriesDao {

    @Transaction
    @Query("SELECT * FROM ${PennyDatabase.Tables.CATEGORIES}")
    fun getAllWithTransactions(): Flow<List<CategoryWithTransactions>>

    @Query("SELECT * FROM ${PennyDatabase.Tables.CATEGORIES} WHERE id = :id")
    fun getByIdWithTransactions(id: Long): Flow<CategoryWithTransactions>

    @Query("SELECT * FROM ${PennyDatabase.Tables.CATEGORIES}")
    fun getAll(): Flow<List<CategoryEntity>>

    @Query("SELECT * FROM ${PennyDatabase.Tables.CATEGORIES} WHERE LOWER(name) = LOWER(:name) LIMIT 1")
    fun getByName(name: String): Flow<CategoryEntity?>

    @Query("DELETE FROM ${PennyDatabase.Tables.CATEGORIES} WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Upsert
    suspend fun upsertCategory(categoryEntity: CategoryEntity): Long
}
