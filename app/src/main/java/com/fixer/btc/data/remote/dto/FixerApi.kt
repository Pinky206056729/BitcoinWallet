package com.fixer.btc.data.remote.dto

import retrofit2.http.GET
import retrofit2.http.Path
import retrofit2.http.Query

interface FixerApi {
    @GET("convert")
    suspend fun getCoins(
        @Query("to") to: String,
        @Query("from") from: String,
        @Query("amount") amount: String
    ):ConvertDto


    @GET("fluctuation")
    suspend fun getFluctuation(
        @Query("start_date") startDate: String,
        @Query("end_date") endDate: String,
        @Query("base") base: String,
        @Query("symbols") symbols: String
    ):FluctuationDto

}