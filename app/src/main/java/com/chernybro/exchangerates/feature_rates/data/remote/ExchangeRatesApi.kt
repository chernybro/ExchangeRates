package com.chernybro.exchangerates.feature_rates.data.remote

import com.chernybro.exchangerates.feature_rates.data.remote.dto.LatestRatesDTO
import retrofit2.http.GET
import retrofit2.http.Query


interface ExchangeRatesApi {

    @GET("/latest")
    suspend fun getLatestRates(
        @Query("base") base: String
    ): LatestRatesDTO
}