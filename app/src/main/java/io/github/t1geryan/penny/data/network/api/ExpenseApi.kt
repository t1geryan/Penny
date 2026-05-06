package io.github.t1geryan.penny.data.network.api

import io.github.t1geryan.penny.data.network.dto.TransactionsResponseDto
import retrofit2.http.GET

interface ExpenseApi {

    @GET("expense-tracker")
    suspend fun getTransactions(): TransactionsResponseDto
}
