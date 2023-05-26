package com.fixer.btc.domain.model

data class CurrentRateUiModel(
    val currency: List<Currency> = emptyList(),
    val error: String? = null,
    val loading: Boolean = false
)
