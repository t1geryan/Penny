package io.github.t1geryan.penny.data.network.dto

import com.google.gson.annotations.SerializedName

data class TransactionsResponseDto(
    @SerializedName("transactions") val transactions: List<TransactionDto>,
)

data class TransactionDto(
    @SerializedName("id") val id: String,
    @SerializedName("title") val title: String,
    @SerializedName("amount") val amount: Double,
    @SerializedName("currency") val currency: String,
    @SerializedName("category") val category: String,
    @SerializedName("date") val dateIso: String,
    @SerializedName("updatedAt") val updatedAtIso: String,
)
