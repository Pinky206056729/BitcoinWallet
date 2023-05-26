package com.fixer.btc.domain.model.use_case

import com.fixer.btc.domain.model.Currency
import com.fixer.btc.domain.model.repository.CurrencyRepository
import com.fixer.btc.util.Resource
import retrofit2.HttpException
import java.io.IOException
import javax.inject.Inject

class GetConvertedCurrencies @Inject constructor(private val currencyRepository: CurrencyRepository) {

    suspend operator fun invoke(amount: Double): Resource<List<Currency>> {
        return try {
            Resource.Success(currencyRepository.getConvertedRates(amount))
        } catch (e: HttpException) {
            Resource.Error(e.localizedMessage ?: "An unexpected error occured")
        } catch (e: IOException) {
            Resource.Error("Couldn't reach server. Check your internet connection.")
        }
    }
}