package com.fixer.btc.domain.model

data class Currency(
    val symbol: String,
    val rate: Double,
    val date: String,
    val fluctuation: Fluctuation
)
