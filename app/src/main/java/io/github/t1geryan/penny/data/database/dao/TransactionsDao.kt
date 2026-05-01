package io.github.t1geryan.penny.data.database.dao

import androidx.room.Dao
import androidx.room.Query
import androidx.room.Upsert
import io.github.t1geryan.penny.data.database.PennyDatabase
import io.github.t1geryan.penny.data.database.entities.TransactionEntity

@Dao
interface TransactionsDao {

    @Query("DELETE FROM ${PennyDatabase.Tables.TRANSACTIONS} WHERE id = :id")
    suspend fun deleteById(id: Long)

    @Upsert
    suspend fun upsertTransaction(transactionEntity: TransactionEntity): Long
}
