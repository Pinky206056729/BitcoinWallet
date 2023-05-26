package com.fixer.btc.data.repository

import com.fixer.btc.data.remote.dto.FixerApi
import com.fixer.btc.domain.model.Currency
import com.fixer.btc.domain.model.Fluctuation
import com.fixer.btc.domain.model.repository.CurrencyRepository
import java.text.SimpleDateFormat
import java.util.Calendar
import java.util.Date
import java.util.Locale
import javax.inject.Inject

class CurrencyRepoImpl @Inject constructor(private val api: FixerApi) : CurrencyRepository {


    override suspend fun getConvertedRates(amount: Double): List<Currency> {

        val currentDate = Date()
        val dfyyyyMMdd = SimpleDateFormat("yyyy-MM-dd", Locale.ENGLISH)
        val calendar = Calendar.getInstance()
        calendar.time = currentDate
        calendar.add(Calendar.DATE, -1);

        val fluctuations = api.getFluctuation(
            startDate = dfyyyyMMdd.format(calendar.time),
            endDate = dfyyyyMMdd.format(currentDate),
            base = "INR",
            symbols = "ZAR,USD,AUD"
        )

        val zar = api.getCoins(from = "INR", to = "ZAR", amount = amount.toString())
        val usd = api.getCoins(from = "INR", to = "USD", amount = amount.toString())
        val aud = api.getCoins(from = "INR", to = "AUD", amount = amount.toString())

        return listOf(zar, usd, aud).map {
            Currency(
                symbol = it.query.to,
                rate = it.result,
                date = it.date,
                fluctuation = when (it.query.to) {
                    "ZAR" -> {
                        Fluctuation(
                            rate = fluctuations.rates.ZAR.change, date = fluctuations.endDate
                        )
                    }

                    "USD" -> {
                        Fluctuation(
                            rate = fluctuations.rates.USD.change, date = fluctuations.endDate
                        )
                    }

                    "AUD" -> {
                        Fluctuation(
                            rate = fluctuations.rates.AUD.change, date = fluctuations.endDate
                        )
                    }

                    else -> {
                        Fluctuation(
                            rate = fluctuations.rates.ZAR.change, date = fluctuations.endDate
                        )
                    }
                }
            )
        }
    }
}