package com.chernybro.exchangerates.feature_rates.data.remote

import com.chernybro.exchangerates.feature_rates.data.remote.dto.LatestRatesDTO
import com.chernybro.exchangerates.feature_rates.data.remote.dto.SymbolsDTO
import com.chernybro.exchangerates.feature_rates.domain.models.Symbol
import retrofit2.http.GET
import retrofit2.http.Query


interface ExchangeRatesApi {

    @GET("/latest")
    suspend fun getLatestRates(
        @Query("base") base: String,
        @Query("symbols") symbols: String? = null
    ): LatestRatesDTO

    @GET("/symbols")
    suspend fun getSymbols() : SymbolsDTO
}