package com.fixer.btc.data.remote.dto

import com.google.gson.annotations.SerializedName

data class FluctuationDto(
    val base: String,
    @SerializedName("end_date")
    val endDate: String,
    val fluctuation: Boolean,
    val rates: Rates,
    @SerializedName("start_date")
    val startDate: String,
    val success: Boolean
)

data class Rates(
    val ZAR: Symbol,
    val USD: Symbol,
    val AUD: Symbol
)

data class Symbol(
    val change: Double,
    @SerializedName("change_pct")
    val changePct: Double,
    @SerializedName("end_rate")
    val endRate: Double,
    @SerializedName("start_rate")
    val startRate: Double
)