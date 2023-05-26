package com.fixer.btc.domain.model.repository

import com.fixer.btc.domain.model.Currency
import com.fixer.btc.domain.model.Fluctuation

interface CurrencyRepository {

    suspend fun getConvertedRates(amount: Double): List<Currency>

}