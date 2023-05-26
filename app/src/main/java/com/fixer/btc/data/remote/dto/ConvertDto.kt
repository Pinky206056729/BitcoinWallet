package com.fixer.btc.data.remote.dto

data class ConvertDto(
    val date: String,
    val historical: Boolean,
    val info: Info,
    val query: Query,
    val result: Double,
    val success: Boolean
)

data class Info(
    val rate: Double,
    val timestamp: Int
)

data class Query(
    val amount: Int,
    val from: String,
    val to: String
)

